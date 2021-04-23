package com.inl.rest.userMgmt;

import com.inl.rest.prototypes.ShellExecutor;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HashManager {
    private static String hashFunctionFilepath = "/home/mcikara/apps/scripts/pw_util/hash.php";
    private static String verifyFunctionFilepath = "/home/mcikara/apps/scripts/pw_util/verify_pw.php";
    
    public String hash(String pw){
        String[] cmdList = {"php", this.hashFunctionFilepath, pw};
        return runShellCmd(cmdList);
    }
    
    public Boolean verify(String pw, String existingHash){
        String[] cmdList = {"php", this.verifyFunctionFilepath, pw, existingHash};
        String verification = runShellCmd(cmdList);
        Boolean boolVerification = Boolean.parseBoolean(verification);
        return boolVerification;
    }
    
    private String runShellCmd(String[] args){
    
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
    
}