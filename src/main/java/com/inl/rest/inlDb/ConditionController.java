package com.inl.rest.inlDB;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.stream.Collectors; 
import java.util.stream.Stream;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

import com.inl.rest.inlDB.domain.*;

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
    
    @GetMapping("/getAllConditionStudyNames")
    public List<String> getAllConditionStudyNames()
    {
        List<Condition> conditionList = conditionRepository.findAll();
        List<String> condNames = conditionList
            .stream()
            .map(c -> c.getStudy())
            .distinct()
            .sorted()
            .collect(Collectors.toList());
        return condNames;
    }
    
    @GetMapping("/getRecentConditionStudyNames")
    public List<String> getRecentConditionStudyNames()
    {
        return conditionRepository.findRecentStudynames();
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
    
    @PostMapping("/addByJSON")
    public String addByJSON(@RequestBody List<Condition> conds)
    {
        try {
            conds.stream()
                .forEach(k -> addCondition(k));
            return "OK";
        } catch(Exception exception_name) {
            return "not OK";
        }
    }
    
    @PostMapping("/addCondition")
    public Condition addCondition(Condition condition)
    {
        return conditionRepository.save(condition);
    }
    
    @PostMapping("/updateByJSON")
    public String updateByJSON(@RequestBody List<Condition> conds)
    {
        try {
            conds.stream()
                .forEach(k -> updateCondition(k.getId(), k));
            return "OK";
        } catch(Exception exception_name) {
            return "not OK";
        }
    }
    
    @PostMapping("/updateCondition/{id}")
    public Condition updateCondition(@PathVariable(value="id") Long id, Condition conditionDetails)
    {
        Optional<Condition> condition = conditionRepository.findById(id);
        Condition condition_new=condition.get();
        condition_new.setStudy(conditionDetails.getStudy());
        condition_new.setCond(conditionDetails.getCond());
        condition_new.setTarget(conditionDetails.getTarget());
        condition_new.setCollected(conditionDetails.getCollected());
        condition_new.setOffset(conditionDetails.getOffset());
        return conditionRepository.save(condition_new);
    
    }

}
