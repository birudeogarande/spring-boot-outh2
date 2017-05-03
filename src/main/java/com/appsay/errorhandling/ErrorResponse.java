package com.appsay.errorhandling;

/**
 * Created by appsay02 on 03-05-2017.
 */
public class ErrorResponse {

    private int status;
    private String message;
    int code;
    String developerMessage;
    String link;

    public ErrorResponse(int status,  int code, String message, String developerMessage, String link) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.developerMessage = developerMessage;
        this.link = link;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}