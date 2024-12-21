package com.sidorov.backspark.exceptions.handlers;

import com.fasterxml.jackson.core.JsonParseException;
import com.sidorov.backspark.exceptions.files.FileException;
import com.sidorov.backspark.exceptions.responses.MessageResponse;
import com.sidorov.backspark.exceptions.socks.SockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class FileExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleSocksException(IOException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<?> handleSocksException(FileException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(ex.getPayload());
    }
}
