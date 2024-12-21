package com.sidorov.backspark.exceptions.responses.socks;

import com.sidorov.backspark.socks.models.Sock;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SockSaveResponse {

    String message;
    Sock sock;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SockErrorResponse [message=");
        builder.append("Failed to save sock:");

        if (sock != null) {
            builder.append(", sock=");
            builder.append(sock);
        }

        builder.append("]");
        return builder.toString();
    }
}
