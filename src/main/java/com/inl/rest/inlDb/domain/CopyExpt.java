package com.inl.rest.inlDB.domain;

import com.inl.rest.prototypes.ExptSpawner;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.nio.file.Paths;

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
        
        this.replacements.put(this.placeholders[0], platformArg);
        this.replacements.put(this.placeholders[1], protocolArg);
        this.replacements.put(this.placeholders[2], exptName);
    }
    
}

