package com.sidorov.backspark.exceptions.socks;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class SockException extends RuntimeException {

    HttpStatus status;
    Object payload;

    public SockException( Object payload, HttpStatus status) {
        super(payload.toString());
        this.payload = payload;
        this.status = status;
    }
}
