package com.futnorte.torneo.infrastructure.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    private String code;
    private String message;
    private String traceId;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, String> details;
    
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    
    public ErrorResponse(String code, String message, String traceId, String path) {
        this(code, message);
        this.traceId = traceId;
        this.path = path;
    }
    
    public ErrorResponse(String code, String message, String traceId, String path, Map<String, String> details) {
        this(code, message, traceId, path);
        this.details = details;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getTraceId() {
        return traceId;
    }
    
    public String getPath() {
        return path;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public Map<String, String> getDetails() {
        return details;
    }
}