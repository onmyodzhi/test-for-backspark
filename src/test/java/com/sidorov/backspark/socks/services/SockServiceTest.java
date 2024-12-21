package com.sidorov.backspark.socks.services;

import com.sidorov.backspark.exceptions.files.FileProcessingException;
import com.sidorov.backspark.exceptions.socks.InvalidSockDataException;
import com.sidorov.backspark.exceptions.socks.SockAlreadyExistsException;
import com.sidorov.backspark.exceptions.socks.SockNotFoundException;
import com.sidorov.backspark.socks.models.DTOs.SockDTO;
import com.sidorov.backspark.socks.models.Sock;
import com.sidorov.backspark.socks.models.mappers.SockMapper;
import com.sidorov.backspark.socks.repositories.SockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SockServiceTest {

    @Mock
    SockRepository sockRepository;

    @Mock
    SockMapper sockMapper;

    @InjectMocks
    SockService sockService;

    @Test
    void getCountOfSockByFilter_returnsCorrectCountOfSocks() {
        Map<String, String> params = Map.of("color", "red", "cottonPercentage", "80");

        Sock sock = new Sock();
        sock.setColor("RED");
        sock.setCottonPercentage((short) 80);
        sock.setCount(10L);

        Sock sock1 = new Sock();
        sock1.setColor("RED");
        sock1.setCottonPercentage((short) 80);
        sock1.setCount(15L);

        List<Sock> socks = List.of(sock, sock1);

        when(sockRepository.findAll(any(Specification.class))).thenReturn(socks);

        Long result = sockService.getCountOfSockByFilter(params);

        assertEquals(25L, result);
        verify(sockRepository, times(1))
                .findAll(any(Specification.class));
    }

    @Test
    void increaseCountOfSocks_ShouldIncreaseCountOfSocks() {
        Map<String, String> params = Map.of("color", "red", "cottonPercentage", "80");

        Sock sock = new Sock();
        sock.setColor("RED");
        sock.setCottonPercentage((short) 80);
        sock.setCount(10L);

        when(sockRepository.findOne(any(Specification.class)))
                .thenReturn(Optional.of(sock));

        sockService.increaseCountOfSocks(params, 10L);

        assertEquals(20L, sock.getCount());
        verify(sockRepository, times(1)).save(sock);
    }

    @Test
    void increaseCountOfSocks_ShouldThrowExceptionWhenSockIsNotFound() {
        Map<String, String> params = Map.of("color", "red", "cottonPercentage", "80");

        when(sockRepository.findOne(any(Specification.class)))
                .thenReturn(Optional.empty());

        assertThrows(SockNotFoundException.class,
                () -> sockService.increaseCountOfSocks(params, 10L));
    }

    @Test
    void decreaseCountOfSocks_ShouldDecreaseCountOfSocks() {
        Map<String, String> params = Map.of("color", "red", "cottonPercentage", "80");

        Sock sock = new Sock();
        sock.setColor("RED");
        sock.setCottonPercentage((short) 80);
        sock.setCount(10L);

        when(sockRepository.findOne(any(Specification.class)))
                .thenReturn(Optional.of(sock));

        sockService.decreaseCountOfSocks(params, 10L);
        assertEquals(0L, sock.getCount());
    }

    @Test
    void decreaseCountOfSocks_ShouldThrowExceptionWhenSockIsNotFound() {
        Map<String, String> params = Map.of("color", "red", "cottonPercentage", "80");

        when(sockRepository.findOne(any(Specification.class)))
                .thenReturn(Optional.empty());

        assertThrows(SockNotFoundException.class,
                () -> sockService.decreaseCountOfSocks(params, 10L));
    }


    @Test
    void editSock_ShouldEditSock() {
        Long id = 1L;

        SockDTO sockDTO = new SockDTO(null, 25L, (short) 60, "WHITE");

        Sock existingSock = new Sock();
        existingSock.setColor("RED");
        existingSock.setCottonPercentage((short) 80);
        existingSock.setCount(10L);

        Sock newSock = new Sock();
        newSock.setColor("WHITE");
        newSock.setCottonPercentage((short) 60);
        newSock.setCount(25L);

        when(sockRepository.findById(id)).thenReturn(Optional.of(existingSock));
        when(sockMapper.toEntity(sockDTO)).thenReturn(newSock);
        when(sockRepository.existsSock(newSock)).thenReturn(Optional.empty());
        when(sockRepository.save(newSock)).thenReturn(newSock);
        when(sockMapper.toDTO(newSock)).thenReturn(sockDTO);

        SockDTO result = sockService.editSock(id, sockDTO);

        assertEquals(sockDTO, result);
        verify(sockRepository, times(1)).save(newSock);
    }

    @Test
    void editSock_ShouldThrowExceptionWhenSockIsExists() {
        Long id = 1L;

        SockDTO sockDTO = new SockDTO(null, 25L, (short) 60, "WHITE");

        Sock existingSock = new Sock();
        existingSock.setColor("RED");
        existingSock.setCottonPercentage((short) 80);
        existingSock.setCount(10L);

        Sock newSock = new Sock();
        newSock.setColor("WHITE");
        newSock.setCottonPercentage((short) 60);
        newSock.setCount(25L);

        when(sockRepository.findById(id)).thenReturn(Optional.of(existingSock));
        when(sockMapper.toEntity(sockDTO)).thenReturn(newSock);
        when(sockRepository.existsSock(newSock)).thenReturn(Optional.of(existingSock));

        assertThrows(SockAlreadyExistsException.class,
                () -> sockService.editSock(id, sockDTO));
    }

    @Test
    void processSockBatch_ShouldProcessFile() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        String csvContent = "RED,80,10\nBLACK,70,20\n";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());

        when(file.getInputStream()).thenReturn(inputStream);

        sockService.processSockBatch(file);

        verify(sockRepository, times(2))
                .save(any(Sock.class));

    }

    @Test
    void processSockBatch_shouldThrowFileProcessingException() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenThrow(new IOException("File cannot be read"));

        assertThrows(FileProcessingException.class, () -> sockService.processSockBatch(file));
    }


    @Test
    void processSockBatch_shouldThrowInvalidSockDataException() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        String invalidCsvContent = "Red,10\nBlue";
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(invalidCsvContent.getBytes()));

        assertThrows(InvalidSockDataException.class, () -> sockService.processSockBatch(file));
    }
}
