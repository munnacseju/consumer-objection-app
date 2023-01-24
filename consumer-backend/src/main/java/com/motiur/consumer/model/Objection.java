package com.motiur.consumer.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GenerationType;

@Entity
@Table(name = "objections")
public class Objection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "objection_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String imageBase64;

    @Column(columnDefinition = "TEXT")
    private String videoBase64;

    @Column(columnDefinition = "TEXT")
    private String audioBase64;
    
    private String objectionDetails;
    private String objectionType;
    private String accusedOrganizationName;
    private String accusedOrganizationAddress;

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp time = new Timestamp(System.currentTimeMillis());

	@ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Boolean isVerified;
    private Boolean isClosed;
	
    public Objection() {

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
