package com.sidorov.backspark.exceptions.socks;

import com.sidorov.backspark.exceptions.responses.socks.NotEnoughSocksResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NotEnoughSocksException extends SockException {

    public NotEnoughSocksException() {
        super(new NotEnoughSocksResponse("Insufficient stock available. Requested quantity exceeds available stock."),
                HttpStatus.CONFLICT);
    }
}
