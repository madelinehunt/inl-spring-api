package com.inl.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; 
import java.util.stream.Stream; 
import java.util.Date;

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
    
    @PostMapping("/recordParticipant")
    public Participant recordParticipant(Participant participant)
    {
        Date date = new Date(System.currentTimeMillis());
        participant.setDateTime(date);
        return participantRepository.save(participant);
    }
    
    
    

}
