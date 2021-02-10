package com.inl.rest;

import com.inl.rest.domain.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConditionRepository extends JpaRepository<Condition, Long>{

    List<Condition> findByStudy(String study);
    
    @Query("SELECT DISTINCT study from Condition")
    List<String> findDistinctStudy();

}
