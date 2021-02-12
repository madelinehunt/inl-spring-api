package com.inl.rest;

import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors; 
import java.util.stream.Stream;
import java.util.Comparator;
import java.util.Random;

import com.inl.rest.domain.*;

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

@RestController
public class ConditionController {
    
    @Autowired
    ConditionRepository conditionRepository;

    @GetMapping("/getConditionsByStudy")
    public List<Condition> getConditionsByStudy(@RequestParam String study)
    {
        return conditionRepository.findByStudy(study);
    }
    
    @GetMapping("/getWeightedConditionByStudy")
    public Condition getWeightedConditionByStudy(@RequestParam String study)
    {
        List<Condition> conds = conditionRepository.findByStudy(study);
        Comparator<Condition> comp = Comparator.comparing(Condition::getWeightedness);
        return conds.stream().max(comp).get();
    }
    
    @GetMapping("/getSampledConditionByStudy")
    public Condition getSampledConditionByStudy(@RequestParam String study)
    {
        List<Condition> conds = conditionRepository.findByStudy(study);
        Random rand = new Random();
        return conds.get(rand.nextInt(conds.size()));
    }
    
    @GetMapping("/getAllCbalStudies")
    public List<String> getDistinctStudy()
    {
        return conditionRepository.findDistinctStudy();
    }    

}
