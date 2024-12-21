package com.sidorov.backspark.exceptions.responses.files;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FIleErrorResponse {

    String message;
    String name;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FileErrorResponse [message=");
        builder.append(message);

        if (name != null) {
            builder.append(", name=");
            builder.append(name);
        }

        builder.append("]");
        return builder.toString();
    }
}
