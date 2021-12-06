package com.prediction.weather.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ErrorResponse {
    private int status;
    private String message;
    private String requestPath;
    private ErrorResponse cause;

    public ErrorResponse(int value, String message, String htmlEscape) {
        this.status = value;
        this.message = message;
        this.requestPath = htmlEscape;
    }
}
