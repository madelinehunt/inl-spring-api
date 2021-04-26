package com.inl.inlDB.rest;

import com.inl.rest.inlDB.domain.NewExpt;
import com.inl.rest.inlDB.domain.CopyExpt;

import java.util.List;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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
    
    @PostMapping("/copyExptRepo")
    public String copyExptRepo(CopyExpt copyExpt) {
        copyExpt.copyRepoAlreadyExists();
        copyExpt.cloneToLive();
        copyExpt.replacePlaceholders();
        return "great success";
    }
    
    @GetMapping("/getRepoNames")
    public List<String> getRepoNames(String target) {
        String rootOfPath = "/home/mcikara/apps/expts/repos/";
        String fullTarget = (new StringBuilder()).append(rootOfPath).append(target).toString();
        File f = new File(fullTarget);
        return Arrays.asList(f.list());
    }
}