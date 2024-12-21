package com.sidorov.backspark.exceptions.socks;

import com.sidorov.backspark.exceptions.responses.socks.SockSaveResponse;
import com.sidorov.backspark.socks.models.Sock;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SockSaveException extends SockException{

    public SockSaveException(Sock sock) {
        super(new SockSaveResponse("Failed to save sock: " + sock, sock),
                HttpStatus.BAD_REQUEST);
    }
}
