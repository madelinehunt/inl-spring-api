package com.inl.rest.inlDB;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream; 
import java.util.Date;
import java.util.Set;
import java.math.BigInteger;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collections;
import java.util.function.Function;
import java.util.function.Predicate;

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

import javax.servlet.http.HttpServletRequest;

@RestController
public class ParticipantController {
    
    @Autowired
    ParticipantRepository participantRepository;
    
    @GetMapping("/getAllParticipants")
    public List<Participant> getAllParticipants()
    {
        return participantRepository.findAll();
    }
    
    @GetMapping("/getParticipantById/{id}")
    public Optional<Participant> getParticipantById(@PathVariable(value="id") Long id)
    {
        return participantRepository.findById(id);
    }
    
    @GetMapping("/getParticipantsByStudy")
    public List<Participant> getParticipantsByStudy(@RequestParam String study)
    {
        return participantRepository.findByStudy(study);
    }
    
    @GetMapping("/getAllStudies")
    public List<String> getDistinctStudy()
    {
        return participantRepository.findDistinctStudy();
    }
    
    @GetMapping("/getAllProtocols")
    public List<String> getDistinctProtocols()
    {
        return participantRepository.findDistinctProtocol();
    }
    
    @GetMapping("/getMinDate")
    public List<String> getMinDate()
    {
        return participantRepository.findMinDate();
    }
    
    @PostMapping("/recordParticipant")
    public Participant recordParticipant(Participant participant)
    {
        Date date = new Date(System.currentTimeMillis());
        participant.setDateTime(date);
        return participantRepository.save(participant);
    }
    
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
    
    @GetMapping("/getPCountsBasedOnDate")
    public List<Map<String, BigInteger>> getPCountsBasedOnDate(@RequestParam String beginDate, @RequestParam String endDate)
    {
        // long beginTime = Long.parseLong(beginDate);
        // long endTime = Long.parseLong(endDate);
        // Date parsedStartDate = new Date();
        // Date parsedEndDate = new Date();
        // parsedStartDate.setTime(beginTime);
        // parsedEndDate.setTime(endTime);
        // 
        // DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
        // 
        // List<Participant> results = participantRepository.findPCountsByDate(parsedStartDate, parsedEndDate);
        // 
        // // collapses Participant objects into simple ID--protocol pairs
        // Map<String, String> idProtPairs = new HashMap<>();
        // results.stream()
        //     .forEach(p -> idProtPairs.put(p.getPlatformID(), p.getProtocol()));
        // 
        // Map<String, Long> frequencyMap = new HashMap<>();
        // 
        // idProtPairs.values()
        //     .stream()
        //     .forEach(
        //         v -> frequencyMap.put(
        //             v, Long.valueOf(Collections.frequency(idProtPairs.values(), v))
        //         )
        //     );
            
        // Map<String, Long> resultsMap = results.stream()
        //     .filter(distinctByKey(Participant::getPlatformID))
        //     .collect(Collectors.groupingBy(Participant::getProtocol, Collectors.counting()));
        
        long beginTime = Long.parseLong(beginDate);
        long endTime = Long.parseLong(endDate);
        Date parsedStartDate = new Date();
        Date parsedEndDate = new Date();
        parsedStartDate.setTime(beginTime);
        parsedEndDate.setTime(endTime);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
        
        List<Map<String, BigInteger>> results = participantRepository.findPCountsByDate(parsedStartDate, parsedEndDate);
        
        return results;
    }
    
}
