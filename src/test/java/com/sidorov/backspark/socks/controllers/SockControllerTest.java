package com.sidorov.backspark.socks.controllers;

import com.sidorov.backspark.socks.services.SockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SockControllerImpl.class)
class SockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SockService sockService;

    @Test
    void getSocks_shouldReturnCount() throws Exception {
        Map<String, String> params = Map.of("color", "red", "size", "42");
        when(sockService.getCountOfSockByFilter(params)).thenReturn(10L);

        mockMvc.perform(get("/api/socks")
                        .param("color", "red")
                        .param("size", "42"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));

        verify(sockService).getCountOfSockByFilter(params);
    }

    @Test
    void batchSocks_shouldReturnOk() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "socks.csv", "text/csv", "data".getBytes());

        mockMvc.perform(multipart("/api/socks/batch").file(file))
                .andExpect(status().isOk());

        verify(sockService).processSockBatch(any(MultipartFile.class));
    }
}
