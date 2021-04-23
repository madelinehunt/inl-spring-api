package com.inl.rest.inlDB;

import com.inl.rest.inlDB.domain.*;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParticipantRepository extends JpaRepository<Participant, Long>{

    List<Participant> findByStudy(String study);
    
    @Query("SELECT DISTINCT study from Participant")
    List<String> findDistinctStudy();
    
    @Query("SELECT DISTINCT protocol from Participant")
    List<String> findDistinctProtocol();

    @Query("SELECT MIN(datetime) from Participant")
    List<String> findMinDate();
    
    // @Query("SELECT COUNT(DISTINCT platformID) AS Count, protocol AS Protocol FROM Participant WHERE (datetime BETWEEN :beginDate and :endDate) GROUP BY protocol")
    // @Query(value="SELECT * FROM participant_counts WHERE (datetime BETWEEN :beginDate and :endDate) AND platformID NOT LIKE '%test%' ", nativeQuery=true)
    // @Query(value="SELECT COUNT(DISTINCT platformID) AS Count, protocol AS Protocol FROM participant_counts WHERE (datetime BETWEEN :beginDate and :endDate) AND platformID NOT LIKE '%test%' GROUP BY platformID, protocol", nativeQuery=true)
    // @Query(value="SELECT COUNT(Distinct platformID), protocol FROM (SELECT platformID, protocol from participant_counts WHERE(datetime BETWEEN :beginDate and :endDate)and platformID NOT LIKE '%test%' GROUP by platformID, protocol) as intQuery GROUP by protocol", nativeQuery=true)
    @Query(value="SELECT protocol, COUNT(Distinct platformID) from participant_counts WHERE(datetime BETWEEN :beginDate and :endDate) and platformID NOT LIKE '%test%' GROUP by protocol", nativeQuery=true)
    List<Map<String, BigInteger>> findPCountsByDate(Date beginDate, Date endDate);

}
