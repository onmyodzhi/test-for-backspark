package com.sidorov.backspark.socks.models.DTOs;

import lombok.Value;

/**
 * DTO for {@link com.sidorov.backspark.socks.models.Sock}
 */
@Value
public class SockDTO {
    Long id;
    Long count;
    Short cottonPercentage;
    String color;
}