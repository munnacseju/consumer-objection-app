package com.motiur.consumer.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Objection implements Serializable {
    private String imageBase64;
    private String audioBase64;
    private String price;
    private long id;
    private Timestamp timestamp;
    private String videoBase64;

    public Objection(){}

    public Objection(String imageBase64, String audioBase64, String videoBase64, String price) {
        this.imageBase64 = imageBase64;
        this.audioBase64 = audioBase64;
        this.videoBase64 = videoBase64;
        this.price = price;
    }

    public Objection(String imageBase64, String audioBase64, String price, long id, Timestamp timestamp) {
        this.imageBase64 = imageBase64;
        this.audioBase64 = audioBase64;
        this.price = price;
        this.id = id;
        this.timestamp = timestamp;
    }
    public String getVideoBase64() {
        return videoBase64;
    }

    public void setVideoBase64(String videoBase64) {
        this.videoBase64 = videoBase64;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getAudioBase64() {
        return audioBase64;
    }

    public void setAudioBase64(String audioBase64) {
        this.audioBase64 = audioBase64;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
