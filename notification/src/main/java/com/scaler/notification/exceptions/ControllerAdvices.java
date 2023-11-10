package com.scaler.notification.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
     @ExceptionHandler(CustomInvalidStatusException.class)
    private ResponseEntity<?> handleInvalidStatus(CustomInvalidStatusException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<String> handleEmailSendingException(EmailSendingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error sending email: " + e.getMessage());
    }

    @ExceptionHandler(SMSSendingException.class)
    public ResponseEntity<String> handleSMSSendingException(SMSSendingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error sending SMS: " + e.getMessage());
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleInvalidEmailException(InvalidEmailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid Email: " + e.getMessage());
    }

    @ExceptionHandler(NotificationCreationException.class)
    public ResponseEntity<String> handleNotificationCreationException(NotificationCreationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong from our end in creating the Notification request. Please try again later.");
    }

    @ExceptionHandler(NotificationUpdateException.class)
    public ResponseEntity<String> handleNotificationUpdateException(NotificationUpdateException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong from our end in updating the Notification request. Please try again later.");
    }

}
