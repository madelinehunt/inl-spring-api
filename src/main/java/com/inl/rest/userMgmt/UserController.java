package com.inl.rest.userMgmt;

import com.inl.rest.userMgmt.HashManager;
import com.inl.rest.userMgmt.Emailer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; 
import java.util.stream.Stream; 
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.jpa.repository.Query;

import javax.servlet.http.HttpServletRequest;


@RestController
public class UserController {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    Emailer emailer;
    
    HashManager hashManager = new HashManager();
    
    private static final char[] POSSIBLE_PW_CHARACTERS = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789#$%*=+?")).toCharArray();
    private static final int NEW_PW_LENGTH = 12;
    
    @GetMapping("/user/getById/{id}")
    public Optional<User> getLabUserById(@PathVariable(value="id") Long id)
    {
        return userRepository.findById(id);
    }
    
    @GetMapping("/user/getPwHash/{id}")
    public String getPwHashById(@PathVariable(value="id") Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get().getPwHash();
        } else {
            return "Error: User not found";
        }
    }
    
    @GetMapping("/user/getNewPwHash")
    public String getNewPwHash(@RequestParam String pw) {
        return hashManager.hash(pw);
    }
    
    @GetMapping("user/verifyPw")
    public String verifyPw(@RequestParam String username, @RequestParam String pwAttempt){
        User user = userRepository.findByAccountName(username);
        String existingHash = user.getPwHash();
        Boolean verified = hashManager.verify(pwAttempt, existingHash);
        return verified.toString();
    }
    
    @PostMapping("user/saveNewPw")
    public User saveNewPw(@RequestParam String username, @RequestParam String pw){
        User user = userRepository.findByAccountName(username);
        String newHash = hashManager.hash(pw);
        user.setPwHash(newHash);
        return userRepository.save(user);
    }
    
    @PostMapping("user/createUser")
    public User createUser(User user){
        // this function expects the password to be hashed on the front end, 
        // so the server never sees the bare password
        return userRepository.save(user);
    }
    
    @PostMapping("user/passwordReset")
    public String passwordReset(String emailAddress){
        Optional<User> userTry = userRepository.findByEmailAddress(emailAddress);
        if(userTry.isPresent()) {
            User user = userTry.get();
            String newPassword = this.generateNewPassword(this.NEW_PW_LENGTH);
            user.setPwHash(hashManager.hash(newPassword));
            userRepository.save(user);
            return emailer.sendMail(
                user.getEmailAddress(),
                user.getAccountName(),
                newPassword
            );
        } else {
            return "Error: User not found";
        }
    }
    
    private String generateNewPassword(int strLength){
        String randomStr = RandomStringUtils.random(
            strLength, 0, POSSIBLE_PW_CHARACTERS.length-1, false, false, POSSIBLE_PW_CHARACTERS, new SecureRandom() 
        );
        return randomStr;
    }
}