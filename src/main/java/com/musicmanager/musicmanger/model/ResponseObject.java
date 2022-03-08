package com.musicmanager.musicmanger.model;

public class ResponseObject {
    private String status;
    private String message;
    private Object dataA;
    private Object dataB;
    public ResponseObject(){

    }
    public ResponseObject(String status, String message, Object dataA) {
        this.status = status;
        this.message = message;
        this.dataA = dataA;
    }
    public ResponseObject(String status, String message, Object dataA, Object dataB) {
        this.status = status;
        this.message = message;
        this.dataA = dataA;
        this.dataB = dataB;
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
    public Object getDataA() {
        return dataA;
    }
    public void setDataA(Object dataA) {
        this.dataA = dataA;
    }
    public Object getDataB() {
        return dataB;
    }
    public void setDataB(Object dataB) {
        this.dataB = dataB;
    }
  
    
}
