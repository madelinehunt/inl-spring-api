package com.inl.rest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="participant_counts")
public class Participant {
    @Id
    @GeneratedValue(strategy = IDENTITY)
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

    public String getPlatformID() {
        return platformID;
    }

    public void setPlatformID(String platformID) {
        this.platformID = platformID;
    }

    public String getSubjID() {
        return subjID;
    }

    public void setSubjID(String subjID) {
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
    
    @Override
    public String toString(){
        return "Id - " + id + ", platformID - " + platformID + ", subjID - " + subjID + ", protocol - " + protocol + ", study - " + study + ", datetime - " + datetime;
    }

}
