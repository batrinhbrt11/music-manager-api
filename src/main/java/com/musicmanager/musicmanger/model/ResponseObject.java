package com.musicmanager.musicmanger.model;

public class ResponseObject {
    private String status;
    private String message;
    private Object content;
    private Object totalElement ;
    public ResponseObject(){

    }
    public ResponseObject(String status, String message, Object content) {
        this.status = status;
        this.message = message;
        this.content = content;
    }
    public ResponseObject(String status, String message, Object content, Object totalElement) {
        this.status = status;
        this.message = message;
        this.content = content;
        this.totalElement = totalElement;
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
    public Object getcontent() {
        return content;
    }
    public void setcontent(Object content) {
        this.content = content;
    }
    public Object gettotalElement() {
        return totalElement;
    }
    public void settotalElement(Object totalElement) {
        this.totalElement = totalElement;
    }
  
    
}
