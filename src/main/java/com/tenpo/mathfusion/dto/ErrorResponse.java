package com.tenpo.mathfusion.dto;

public class ErrorResponse {
    private String message;
    private int statusScode;

    public ErrorResponse(String message, int statusScode) {
        this.message = message;
        this.statusScode = statusScode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusScode() {
        return statusScode;
    }

    public void setStatusScode(int statusScode) {
        this.statusScode = statusScode;
    }   
}
