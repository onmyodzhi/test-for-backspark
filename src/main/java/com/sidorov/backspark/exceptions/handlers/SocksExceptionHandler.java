package com.sidorov.backspark.exceptions.handlers;

import com.fasterxml.jackson.core.JsonParseException;
import com.sidorov.backspark.exceptions.socks.SockException;
import com.sidorov.backspark.exceptions.responses.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class SocksExceptionHandler {
    public ResponseEntity<MessageResponse> handleJsonParseException(JsonParseException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(SockException.class)
    public ResponseEntity<?> handleSocksException(SockException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(ex.getPayload());
    }
}
