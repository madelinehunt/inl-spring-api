package com.inl.rest.inlDB.domain;

import com.inl.rest.prototypes.ExptSpawner;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.nio.file.Paths;

public class NewExpt extends ExptSpawner {
    // private static String sourceRepo = "/home/mcikara/lab_software/repos/cl_platform_seed_w_placeholders.git";
    private static String[] placeholders = {
        "•platform•",
        "•protocol•",
        "•studyname•"
    };    
    // private static String sedDelegateLocation = "/home/mcikara/lab_software/.helpers/sed_delegate.py";
    
    public NewExpt(String exptNameArg, String exptFilepathArg, String platformArg, String protocolArg){
        this.exptName = exptNameArg;
        this.exptFilepath = exptFilepathArg;
        this.platform = platformArg;
        this.protocol = protocolArg;
        
        this.sourceRepo = "/home/mcikara/lab_software/repos/cl_platform_seed_w_placeholders.git";
        this.sedDelegateLocation = "/home/mcikara/lab_software/.helpers/sed_delegate.py";

        this.repoPathRoot = Paths.get(this.repoPathRootString);
        this.livePathRoot = Paths.get(this.livePathRootString);
        
        this.replacements.put(this.placeholders[0], platformArg);
        this.replacements.put(this.placeholders[1], protocolArg);
        this.replacements.put(this.placeholders[2], exptName);
    }
    
}

