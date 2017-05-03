package com.appsay.errorhandling;

public class AppException extends Exception {

    private static final long serialVersionUID = -8999932578270387947L;
    /**
     * contains redundantly the HTTP status of the response sent back to the
     * client in case of error, so that the developer does not have to look into
     * the response headers. If null a default
     */
    Integer status;
    /**
     * application specific error code
     */
    int code;
    /**
     * link documenting the exception
     */
    String link;
    /**
     * detailed error description for developers
     */
    String developerMessage;
    String field1;
    String field2;

    /**
     * @param status
     * @param code
     * @param message
     * @param developerMessage
     * @param link
     */
    public AppException(int status, int code, String message,
                        String developerMessage, String link) {
        super(message);
        this.status = status;
        this.code = code;
        this.developerMessage = developerMessage;
        this.link = link;
    }
    public AppException(int status, int code, String message,
                        String developerMessage, String link,String field1,String field2) {
        super(message);
        this.status = status;
        this.code = code;
        this.developerMessage = developerMessage;
        this.link = link;
        this.field1 = field1;
        this.field2 = field2;
    }
    public AppException(String message, String developerMessage) {
        super(message);
        this.developerMessage = developerMessage;

    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

}
