
package com.tenpo.mathfusion.dto;

public class ErrorResponse {
    private int statusCode;
    private String message;
    private String error;
    private long timestamp;
    private String path;

    public ErrorResponse() {
    }

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorResponse(int statusCode, String message, String error, long timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;
        this.timestamp = timestamp;
    }

    public ErrorResponse(int statusCode, String message, String error, long timestamp, String path) {
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;
        this.timestamp = timestamp;
        this.path = path;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;

    }

    public void setError(String error) {
        this.error = error;
    }

    public long getTimestamp() {
        return timestamp;

    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;

    }

    public void setPath(String path) {
        this.path = path;
    }

}