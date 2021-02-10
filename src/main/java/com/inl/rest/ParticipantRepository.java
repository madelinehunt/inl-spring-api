package com.inl.rest;

import com.inl.rest.domain.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParticipantRepository extends JpaRepository<Participant, Long>{

    List<Participant> findByStudy(String study);
    
    @Query("SELECT DISTINCT study from Participant")
    List<String> findDistinctStudy();

}
