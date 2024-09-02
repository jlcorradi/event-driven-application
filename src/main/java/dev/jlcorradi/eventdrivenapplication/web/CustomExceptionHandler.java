package dev.jlcorradi.eventdrivenapplication.web;

import dev.jlcorradi.eventdrivenapplication.common.exceptions.CustomException;
import dev.jlcorradi.eventdrivenapplication.common.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    public static final String GENERIC_ERR_MSG = "An error occurred while executing the operation.";

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Void> handleCustomException(CustomException e) {
        log.error("Error handled by CustomExceptionHandler.", e);

        if (e instanceof EntityNotFoundException entityNotFoundException) {
            HttpUtils.addMessageHeader(MessageType.ERROR, entityNotFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpUtils.addMessageHeader(MessageType.ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleRawException(Exception e) {
        log.error("Error handled by CustomExceptionHandler.", e);

        HttpUtils.addMessageHeader(MessageType.ERROR, GENERIC_ERR_MSG);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}