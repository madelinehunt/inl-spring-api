package com.inl.rest;

import com.inl.rest.domain.NewExpt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FileSystemController {
    
    @PostMapping("/createNewExptRepo")
    public String createNewExptRepo(NewExpt newExpt) {
        newExpt.copyRepo();
        newExpt.cloneToLive();
        newExpt.replacePlaceholders();
        return "great success";
    }
}