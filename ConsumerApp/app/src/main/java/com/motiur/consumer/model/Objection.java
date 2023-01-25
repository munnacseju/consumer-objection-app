package com.motiur.consumer.model;

import java.sql.Timestamp;

public class Objection {

    private Long id;

    private String imageBase64;

    private String videoBase64;

    private String audioBase64;

    private String objectionDetails;
    private String objectionType;
    private String accusedOrganizationName;
    private String accusedOrganizationAddress;

    private Timestamp time;
    private User user;

    private Boolean isVerified;
    private Boolean isClosed;

    public Objection() {

    }

    public Objection(String imageBase64, String videoBase64, String audioBase64, String objectionDetails) {
        this.imageBase64 = imageBase64;
        this.videoBase64 = videoBase64;
        this.audioBase64 = audioBase64;
        this.objectionDetails = objectionDetails;
    }

    public Objection(Long id, String imageBase64, String videoBase64, String audioBase64, String objectionDetails,
                     String objectionType, String accusedOrganizationName, String accusedOrganizationAddress, Timestamp time,
                     User user) {
        super();
        this.id = id;
        this.imageBase64 = imageBase64;
        this.videoBase64 = videoBase64;
        this.audioBase64 = audioBase64;
        this.objectionDetails = objectionDetails;
        this.objectionType = objectionType;
        this.accusedOrganizationName = accusedOrganizationName;
        this.accusedOrganizationAddress = accusedOrganizationAddress;
        this.time = time;
        this.user = user;
    }

    public String getObjectionDetails() {
        return objectionDetails;
    }


    public void setObjectionDetails(String objectionDetails) {
        this.objectionDetails = objectionDetails;
    }


    public String getObjectionType() {
        return objectionType;
    }


    public void setObjectionType(String objectionType) {
        this.objectionType = objectionType;
    }


    public String getAccusedOrganizationName() {
        return accusedOrganizationName;
    }


    public void setAccusedOrganizationName(String accusedOrganizationName) {
        this.accusedOrganizationName = accusedOrganizationName;
    }


    public String getAccusedOrganizationAddress() {
        return accusedOrganizationAddress;
    }


    public void setAccusedOrganizationAddress(String accusedOrganizationAddress) {
        this.accusedOrganizationAddress = accusedOrganizationAddress;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageBase64() {
        return this.imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getAudioBase64() {
        return this.audioBase64;
    }

    public void setAudioBase64(String audioBase64) {
        this.audioBase64 = audioBase64;
    }

    public String getVideoBase64() {
        return this.videoBase64;
    }

    public void setVideoBase64(String videoBase64) {
        this.videoBase64 = videoBase64;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }
}
