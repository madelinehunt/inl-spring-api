package com.inl.rest.prototypes;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public abstract class ShellExecutor {
    
    protected String runShellCmdRString(String[] args){
        
        try {
            ProcessBuilder pb = new ProcessBuilder(args);
            pb.command(args);

            Process process = pb.start();
            
            StringBuilder output = new StringBuilder();
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );
            
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            };
            
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                return output.toString();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "error!";
    }
    
    protected Boolean runShellCmd(String[] args){
        
        try {
            ProcessBuilder pb = new ProcessBuilder(args);
            pb.command(args);

            Process process = pb.start();
            
            StringBuilder output = new StringBuilder();
            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );
            
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            };
            
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                return true;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public Boolean runShellCmdList(ArrayList<String[]> arrListArgs){
        List<Boolean> boolList = arrListArgs.stream()
            .map(k -> runShellCmd(k))
            .collect(Collectors.toList()); 
        return !boolList.contains(false);
    }
    
}