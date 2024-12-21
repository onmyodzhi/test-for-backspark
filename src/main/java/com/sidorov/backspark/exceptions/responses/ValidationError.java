package com.sidorov.backspark.exceptions.responses;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValidationError extends AppError {
    Map<String, List<String>> errorsDetails;

    public ValidationError(int status, String message, Map<String, List<String>> errorsDetails) {
        super(status, message);
        this.errorsDetails = errorsDetails;
    }
}
