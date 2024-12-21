package com.sidorov.backspark.exceptions.socks;

import com.sidorov.backspark.exceptions.responses.socks.InvalidSockDataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSockDataException extends SockException {
    public InvalidSockDataException(String[] record) {
        super(new InvalidSockDataResponse("Invalid data in record: " + Arrays.toString(record)),
                HttpStatus.BAD_REQUEST);
    }
}
