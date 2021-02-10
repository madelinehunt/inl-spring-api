package com.inl.rest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name="participant_counts")
public class Participant {
    @Id
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "platformID")
    private String platformID;
    
    @Column(name = "subjID")
    private String subjID;
    
    @Column(name = "protocol")
    private String protocol;
    
    @Column(name = "study")
    private String study;
    
    @Column(name = "datetime")
    private Date datetime;
    
    public Participant()
    {
        
    }
    
    public Participant(Long id, String platformID, String subjID, String protocol, String study, Date datetime)
    {
        this.id=id;
        this.platformID=platformID;
        this.subjID=subjID;
        this.protocol=protocol;
        this.datetime=datetime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatformId() {
        return platformID;
    }

    public void setPlatformId(String platformID) {
        this.platformID = platformID;
    }

    public String getSubjId() {
        return subjID;
    }

    public void setSubjId(String subjID) {
        this.subjID = subjID;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    
    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }
    
    public Date getDateTime() {
        return datetime;
    }

    public void setDateTime(Date datetime) {
        this.datetime = datetime;
    }
    

}
