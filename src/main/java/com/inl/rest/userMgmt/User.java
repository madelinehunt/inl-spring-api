package com.inl.rest.userMgmt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "user")
    private String accountName;
    
    @Column(name = "pw_hash")
    private String pwHash;
    
    @Column(name = "name")
    private String fullName;
    
    @Column(name = "email")
    private String emailAddress;
    
    @Column(name = "admin")
    private Long admin;
    
    public User()
    {
        
    }
    
    public User(Long id, String accountName, String pwHash, String fullName,  String emailAddress, Long admin)
    {
        this.id=id;
        this.accountName=accountName;
        this.pwHash=pwHash;
        this.fullName=fullName;
        this.emailAddress=emailAddress;
        this.admin=admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public Long getAdmin() {
        return admin;
    }

    public void setAdmin(Long admin) {
        this.admin = admin;
    }
    
    @Override
    public String toString(){
        String stringRep = (new StringBuilder())
            .append(this.accountName)
            .append("\n")
            .append(this.pwHash)
            .append("\n")
            .append(this.fullName)
            .append("\n")
            .append(this.emailAddress)
            .append("\n")
            .append(this.admin)
            .append("\n").toString();
        return stringRep;
    }

}
