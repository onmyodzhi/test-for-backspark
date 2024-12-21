package com.sidorov.backspark.exceptions.socks;

import com.sidorov.backspark.exceptions.responses.socks.SockAlreadyExistsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SockAlreadyExistsException extends SockException {

    public SockAlreadyExistsException() {
        super(new SockAlreadyExistsResponse("Sock already exist"),
                HttpStatus.CONFLICT);
    }
}
