package com.inl.rest.domain;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NewExpt{
    private String exptName;
    private String exptFilepath;
    private String platform;
    private String protocol;
    
    private static String sourceRepo = "/ops_home/mcikara/lab_software/repos/cl_platform_seed_w_placeholders.git";
    private static String[] placeholders = {
        "•platform•",
        "•protocol•",
        "•studyname•"
    };
    private HashMap<String, String> replacements = new HashMap<String, String>();
    
    private Path repoPathRoot;
    private Path livePathRoot;
    private Path destRepoDir;
    private Path destLiveDir;
    
    private static String sedDelegateLocation = "/ops_home/mcikara/lab_software/.helpers/sed_delegate.py";
    
    public NewExpt(String exptNameArg, String exptFilepathArg, String platformArg, String protocolArg){
        this.exptName = exptNameArg;
        this.exptFilepath = exptFilepathArg;
        this.platform = platformArg;
        this.protocol = protocolArg;
        // these are constants and should never change
        this.repoPathRoot = Paths.get("/ops_home/mcikara/apps/expts/repos/");
        this.livePathRoot = Paths.get("/ops_home/mcikara/apps/expts/live/");
        
        this.replacements.put(this.placeholders[0], platformArg);
        this.replacements.put(this.placeholders[1], protocolArg);
        this.replacements.put(this.placeholders[2], exptName);
    }
    
    public void createParentIfNotExists(Path specificRoot, String path){
        try {
            // check if container dir exists, create it if not
            String containerString = (new StringBuilder()).append(specificRoot.toString()).append("/").append(path).toString();
            
            Path containerPath = Paths.get(containerString);
            if(Files.exists(containerPath) == false) {
                Files.createDirectories(containerPath);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getParent(String path){
        String[] split = path.split("/");
        return split[0];
    }
    
    public String getChild(String path){
        String[] split = path.split("/");
        return split[1];
    }
    
    public Boolean copyRepo(){
        createParentIfNotExists(this.repoPathRoot, getParent(this.exptFilepath));

        String destString = (new StringBuilder())
            .append( this.repoPathRoot.toString() )
            .append("/")
            .append( this.exptFilepath.toString() )
            .append(".git")
            .toString();
        Path destPath = Paths.get(destString);
        this.destRepoDir = destPath;
        
        String[] cmdList = {"cp", "-r", this.sourceRepo, destPath.toString()};
        return runShellCmd(cmdList);
    }

    public Boolean cloneToLive(){
        createParentIfNotExists(this.livePathRoot, getParent(this.exptFilepath));
        
        String destString = (new StringBuilder())
            .append( this.livePathRoot.toString() )
            .append("/")
            .append( this.exptFilepath.toString() )
            .toString();
        Path destPath = Paths.get(destString);
        this.destLiveDir = destPath;
        
        String[] cmdList = {"git", "clone", this.destRepoDir.toString(), destPath.toString()};
        return runShellCmd(cmdList);
    }
    
    public Boolean replacePlaceholders(){        
        // ArrayList<String[]> cmdArrList = new ArrayList<>();
        ArrayList<String> cmdArrListPy = new ArrayList<>();
        
        this.replacements
            .entrySet()
            .stream()
            .forEach((entry)-> {
                StringBuilder sb = new StringBuilder();
                sb.append("'s/");
                sb.append(entry.getKey());
                sb.append("/");
                sb.append(entry.getValue().replace("/", "\\/"));
                sb.append("/g'");
                String sedCommand = sb.toString();
                sb.setLength(0);
                sb.append(this.destLiveDir.toString());
                sb.append("/expt/parameters.js");
                String targetFile = sb.toString();
                
                // String[] strArr = {
                //     this.sedDelegateLocation,
                //     sedCommand,
                //     targetFile
                // };
                // cmdArrList.add(strArr);
                cmdArrListPy.add(sedCommand);
            });
        try {
            Runnable runnable = new Runnable(){
                public void run(){
                    runPythonDelegate(cmdArrListPy);
                }
            };
            new Thread(runnable).start();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }

        // return runShellCmdArrayList(cmdArrList);
        return false;
    }
    
    public Boolean runShellCmd(String[] args){
        
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
    
    public void runPythonDelegate(ArrayList<String> arrListArgs){
        StringBuilder sb = new StringBuilder();
        sb.append("^");
        arrListArgs.stream()
            .forEach(k -> {
                sb.append(k);
                sb.append("^");
            });
        sb.append("^");
        
        String[] strArr = {
            "python3",
            this.sedDelegateLocation,
            this.destLiveDir.toString(),
            sb.toString()
        };
    
        try {
            ProcessBuilder pb = new ProcessBuilder(strArr);
            Process process = pb.start();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}

