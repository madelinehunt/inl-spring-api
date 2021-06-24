package com.inl.rest.inlDB.domain;

import com.inl.rest.prototypes.ExptSpawner;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.nio.file.Paths;
import java.nio.file.Path;

public class CopyExpt extends ExptSpawner {
    private final String[] placeholders = {
        "expt\\.platform.*;",
        "expt\\.protocol.*;",
        "expt\\.studyName.*;"
    };    
    private static String sedDelegateLocation = "/home/mcikara/lab_software/.helpers/sed_delegate.py";
    
    public CopyExpt(String sourceRepo, String exptNameArg, String exptFilepathArg, String platformArg, String protocolArg){
        this.exptName = exptNameArg;
        this.exptFilepath = exptFilepathArg;
        this.platform = platformArg;
        this.protocol = protocolArg;
        
        this.sourceRepo = sourceRepo;
        
        this.repoPathRoot = Paths.get(this.repoPathRootString);
        this.livePathRoot = Paths.get(this.livePathRootString);
        
        // build strings for args
        String platformString = (new StringBuilder())
            .append("expt.platform = \"")
            .append( this.platform )
            .append("\";")
            .toString();
        String protocolString = (new StringBuilder())
            .append("expt.protocol = \"")
            .append( this.protocol )
            .append("\";")
            .toString();
        String exptNameString = (new StringBuilder())
            .append("expt.exptName = \"")
            .append( this.exptName )
            .append("\";")
            .toString();
            
            
        this.replacements.put(this.placeholders[0], platformString);
        this.replacements.put(this.placeholders[1], protocolString);
        this.replacements.put(this.placeholders[2], exptNameString);
        
        String destString = (new StringBuilder())
            .append( this.repoPathRoot.toString() )
            .append("/")
            .append( this.exptFilepath.toString() )
            .append(".git")
            .toString();
        Path destPath = Paths.get(destString);
        this.destRepoDir = destPath;
    }
    
}

