package com.sidorov.backspark.socks.controllers;

import com.sidorov.backspark.socks.controllers.impls.SockController;
import com.sidorov.backspark.socks.models.DTOs.SockDTO;
import com.sidorov.backspark.socks.services.SockService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SockControllerImpl implements SockController {

    SockService sockService;

    @Override
    public ResponseEntity<Long> getSocks(Map<String, String> params) {
        Long result = sockService.getCountOfSockByFilter(params);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> increaseCountOfSocks(Map<String, String> params, Long amount) {
        sockService.increaseCountOfSocks(params, amount);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<HttpStatus> decreaseCountOfSocks(Map<String, String> params, Long amount) {
        sockService.decreaseCountOfSocks(params, amount);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<SockDTO> editSocks(Long id, SockDTO sock) {
        SockDTO changedSock = sockService.editSock(id, sock);
        return ResponseEntity.ok().body(changedSock);
    }

    @Override
    public ResponseEntity<HttpStatus> batchSocks(MultipartFile file) {
        sockService.processSockBatch(file);
        return ResponseEntity.ok().build();
    }
}
