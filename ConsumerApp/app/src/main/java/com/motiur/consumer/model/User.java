package com.motiur.consumer.model;


import java.sql.Timestamp;
import java.util.List;


public class User {

    private Long id;

    private String name;
    private String email;

    private String mobileNumber;

    private String password;
    private List<Objection> objections;

    private String fatherName;
    private String matherName;
    private Timestamp dateOfBirth;
    private String occupation;
    private String presentAddress;
    private String permanentAddress;
    private String nidNumber;

    private Boolean isVerified;
    private String verificationCode;



    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, String mobileNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
    }


    public User(Long id, String name,  String email,  String password,
                List<Objection> objections, String fatherName, String matherName, Timestamp dateOfBirth, String occupation,
                String presentAddress, String permanentAddress, String nidNumber, String mobileNumber) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.objections = objections;
        this.fatherName = fatherName;
        this.matherName = matherName;
        this.dateOfBirth = dateOfBirth;
        this.occupation = occupation;
        this.presentAddress = presentAddress;
        this.permanentAddress = permanentAddress;
        this.nidNumber = nidNumber;
        this.mobileNumber = mobileNumber;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMatherName() {
        return matherName;
    }

    public void setMatherName(String matherName) {
        this.matherName = matherName;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getNidNumber() {
        return nidNumber;
    }

    public void setNidNumber(String nidNumber) {
        this.nidNumber = nidNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Objection> getObjections() {
        return this.objections;
    }

    public void setObjections(List<Objection> objections) {
        this.objections = objections;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


}
