package com.futnorte.torneo.infrastructure.adapters.in.web.config;

import com.futnorte.torneo.domain.exceptions.*;
import com.futnorte.torneo.infrastructure.adapters.in.web.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        String traceId = getOrCreateTraceId();
        
        logger.warn("Entity not found - TraceId: {}, Path: {}, Message: {}", 
                traceId, request.getRequestURI(), ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
                "ENTITY_NOT_FOUND",
                ex.getMessage(),
                traceId,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEntity(DuplicateEntityException ex, HttpServletRequest request) {
        String traceId = getOrCreateTraceId();
        
        logger.warn("Duplicate entity - TraceId: {}, Path: {}, Message: {}", 
                traceId, request.getRequestURI(), ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
                "DUPLICATE_ENTITY",
                ex.getMessage(),
                traceId,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex, HttpServletRequest request) {
        String traceId = getOrCreateTraceId();
        
        logger.warn("Validation error - TraceId: {}, Path: {}, Message: {}", 
                traceId, request.getRequestURI(), ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_ERROR",
                ex.getMessage(),
                traceId,
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRule(BusinessRuleException ex, HttpServletRequest request) {
        String traceId = getOrCreateTraceId();
        
        logger.warn("Business rule violation - TraceId: {}, Path: {}, Message: {}", 
                traceId, request.getRequestURI(), ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
                "BUSINESS_RULE_VIOLATION",
                ex.getMessage(),
                traceId,
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String traceId = getOrCreateTraceId();
        
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            fieldErrors.put(error.getField(), error.getDefaultMessage())
        );
        
        logger.warn("Bean validation failed - TraceId: {}, Path: {}, Fields: {}", 
                traceId, request.getRequestURI(), fieldErrors.keySet());
        
        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_FAILED",
                "Error de validación en los campos enviados",
                traceId,
                request.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler({DataAccessException.class, SQLException.class})
    public ResponseEntity<ErrorResponse> handleDatabaseErrors(Exception ex, HttpServletRequest request) {
        String traceId = getOrCreateTraceId();
        
        logger.error("Database error occurred - TraceId: {}, Path: {}", traceId, request.getRequestURI(), ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
                "DATABASE_ERROR",
                "Error al acceder a la base de datos",
                traceId,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, HttpServletRequest request) {
        String traceId = getOrCreateTraceId();
        
        logger.error("Unexpected error occurred - TraceId: {}, Path: {}", traceId, request.getRequestURI(), ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "Ha ocurrido un error interno en el servidor",
                traceId,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    private String getOrCreateTraceId() {
        String traceId = MDC.get("traceId");
        if (traceId == null) {
            traceId = UUID.randomUUID().toString().substring(0, 8);
            MDC.put("traceId", traceId);
        }
        return traceId;
    }
}