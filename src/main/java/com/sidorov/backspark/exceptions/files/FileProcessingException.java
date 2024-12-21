package com.sidorov.backspark.exceptions.files;

import com.sidorov.backspark.exceptions.responses.files.FilePrecessingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

public class FileProcessingException extends FileException{
    public FileProcessingException(MultipartFile file) {
        super(new FilePrecessingResponse("Something went wrong during processing with file: " + file.getName()),
                HttpStatus.BAD_REQUEST);
    }
}
