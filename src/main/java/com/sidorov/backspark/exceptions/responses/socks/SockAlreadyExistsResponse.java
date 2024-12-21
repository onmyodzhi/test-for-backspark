package com.sidorov.backspark.exceptions.responses.socks;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SockAlreadyExistsResponse {
    String message;
}
