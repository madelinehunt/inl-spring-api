package com.inl.rest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="counterbalancing")
public class Condition {
    @Id
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "study")
    private String study;
    
    @Column(name = "cond")
    private String cond;
    
    @Column(name = "target")
    private Long target;
    
    @Column(name = "collected")
    private Long collected;
    
    @Column(name = "offset")
    private Long offset;
    
    public Condition()
    {
        
    }
    
    public Condition(Long id, String study, String cond, Long target,  Long collected, Long offset)
    {
        this.id=id;
        this.study=study;
        this.cond=cond;
        this.target=target;
        this.collected=collected;
        this.offset=offset;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }
    
    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public Long getTarget() {
        return target;
    }

    public void setTarget(Long target) {
        this.target = target;
    }
    
    public Long getCollected() {
        return collected;
    }

    public void setCollected(Long collected) {
        this.collected = collected;
    }
    
    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }
    
    public Long getWeightedness(){
        return (target - collected) + offset;
    }

}
