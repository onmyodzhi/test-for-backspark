package com.sidorov.backspark.exceptions.socks;

import com.sidorov.backspark.exceptions.responses.socks.SockNotFoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SockNotFoundException extends SockException{
    public SockNotFoundException(Long id) {
        super(new SockNotFoundResponse("Sock with id: " + id + " not found.", id),
                HttpStatus.NOT_FOUND);
    }

    public SockNotFoundException(Map<String, String> params) {
        super(new SockNotFoundResponse("Sock with params: " + params + " not found.", -404L),
                HttpStatus.NOT_FOUND);
    }
}
