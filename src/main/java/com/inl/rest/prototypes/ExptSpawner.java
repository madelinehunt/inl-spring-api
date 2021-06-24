package com.inl.rest.prototypes;

import com.inl.rest.prototypes.ShellExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class ExptSpawner extends ShellExecutor {
    protected String exptName;
    protected String exptFilepath;
    protected String sedDelegateLocation;
    
    protected String platform;
    protected String protocol;
    protected String sourceRepo;
    
    protected String[] placeholders;
    protected HashMap<String, String> replacements = new HashMap<String, String>();
    
    protected Path repoPathRoot;
    protected Path livePathRoot;
    protected Path destRepoDir;
    protected Path destLiveDir;
    
    protected String repoPathRootString = "/home/mcikara/apps/expts/repos/";
    protected String livePathRootString = "/home/mcikara/apps/expts/live/";
    
    public void runSedDelegate(ArrayList<String> arrListArgs){
        // also performs a git pull in destination livedir
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
    
    public Boolean copyRepoAlreadyExists(){        
        String[] cmdList = {"cp", "-r", this.sourceRepo, this.destRepoDir.toString()};
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
                    runSedDelegate(cmdArrListPy);
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
}
    