package com.sidorov.backspark.exceptions.files;

import com.sidorov.backspark.exceptions.responses.files.FilePrecessingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

public class CSVFormatException extends FileException {
    public CSVFormatException(MultipartFile file) {
        super(new FilePrecessingResponse("Format file is not available: " + file.getName()),
                HttpStatus.BAD_REQUEST);
    }
}