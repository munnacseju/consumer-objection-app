package com.motiur.consumer.model;

public class GetObjectionResponse {
    String status;
    String message;
    Objection objection;

    public GetObjectionResponse() {
    }

    public GetObjectionResponse(String status, String message, Objection objection) {
        this.status = status;
        this.message = message;
        this.objection = objection;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Objection getObjection() {
        return objection;
    }

    public void setObjection(Objection objection) {
        this.objection = objection;
    }
}
