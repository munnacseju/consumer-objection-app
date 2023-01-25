package com.motiur.consumer.model;

import java.util.ArrayList;

public class GetAllObjectionResponse {
    private String message;
    private String status;
    private ArrayList<Objection>data;

    public GetAllObjectionResponse(String message, String status, ArrayList<Objection> data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Objection> getData() {
        return data;
    }

    public void setData(ArrayList<Objection> data) {
        this.data = data;
    }
}
