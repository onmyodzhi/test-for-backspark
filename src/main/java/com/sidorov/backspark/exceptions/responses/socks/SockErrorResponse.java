package com.sidorov.backspark.exceptions.responses.socks;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SockErrorResponse {

    String message;
    Long id;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SockErrorResponse [message=");
        builder.append(message);

        if (id != null) {
            builder.append(", id=");
            builder.append(id);
        }

        builder.append("]");
        return builder.toString();
    }
}
