package com.inl.rest.inlDB;

import com.inl.rest.inlDB.domain.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConditionRepository extends JpaRepository<Condition, Long>{

    List<Condition> findByStudy(String study);
    
    @Query("SELECT DISTINCT study from Condition")
    List<String> findDistinctStudy();
    
    @Query("SELECT DISTINCT study from Condition where id > 5055 order by study ASC")
    List<String> findRecentStudynames();

}
