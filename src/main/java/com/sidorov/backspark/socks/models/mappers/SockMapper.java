package com.sidorov.backspark.socks.models.mappers;

import com.sidorov.backspark.socks.models.DTOs.SockDTO;
import com.sidorov.backspark.socks.models.Sock;
import org.mapstruct.*;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface SockMapper {

    Sock toEntity(SockDTO sockDTO);

    SockDTO toDTO(Sock sock);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Sock partialUpdate(SockDTO sockDTO, @MappingTarget Sock sock);
}
