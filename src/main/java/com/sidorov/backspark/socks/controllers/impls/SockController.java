package com.sidorov.backspark.socks.controllers.impls;

import com.sidorov.backspark.socks.models.DTOs.SockDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface SockController {

    @Operation(
            summary = "Get socks",
            description = "Returns the count of socks based on the provided parameters",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Socks found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Long.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid parameters"
                    )
            }
    )
    @GetMapping
    ResponseEntity<Long> getSocks(@RequestParam Map<String, String> params);

    @Operation(
            summary = "Increase count of socks",
            description = "Increases the count of socks based on the given parameters and the 'plus' value",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Socks count increased"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid parameters"
                    )
            }
    )
    @PostMapping("/income")
    ResponseEntity<Void> increaseCountOfSocks(@RequestParam Map<String, String> params,
                                              @RequestParam("plus") Long plus);

    @Operation(
            summary = "Decrease count of socks",
            description = "Decreases the count of socks based on the given parameters and the 'minus' value",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Socks count decreased"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid parameters"
                    )
            }
    )
    @PostMapping("/outcome")
    ResponseEntity<HttpStatus> decreaseCountOfSocks(@RequestParam Map<String, String> params,
                                                    @RequestParam("minus") Long plus);

    @Operation(
            summary = "Edit sock information",
            description = "Updates the information of a specific sock based on its ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sock updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SockDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid sock data"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Sock not found"
                    )
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<SockDTO> editSocks(@PathVariable("id") Long id,
                                      @RequestBody SockDTO sock);

    @Operation(
            summary = "Batch process socks",
            description = "Processes a batch of socks from the provided file",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Batch processed successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid file"
                    )
            }
    )
    @PostMapping("/batch")
    ResponseEntity<HttpStatus> batchSocks(@RequestParam("file") MultipartFile file);
}
