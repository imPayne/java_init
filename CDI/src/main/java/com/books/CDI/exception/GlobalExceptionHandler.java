package com.books.CDI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Gestionnaire d'exceptions global pour gérer les exceptions de manière centralisée.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère les exceptions de type ResourceNotFoundException.
     *
     * @param ex l'exception
     * @param request la requête web
     * @return une réponse HTTP avec les détails de l'erreur
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Gère toutes les autres exceptions.
     *
     * @param ex l'exception
     * @param request la requête web
     * @return une réponse HTTP avec les détails de l'erreur
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

/**
 * Classe représentant les détails d'une erreur.
 */
class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    // Constructeur
    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // Getters et Setters

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
