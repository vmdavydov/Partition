package com.apsoft.partition.controller;

import com.apsoft.partition.exceptions.NotSupportedFormatException;
import com.apsoft.partition.exceptions.WrongNestingSectionException;
import com.apsoft.partition.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Message> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e){
        return ResponseEntity.badRequest().body(new Message("Upload file too large."));
    }

    @ExceptionHandler(NotSupportedFormatException.class)
    public ResponseEntity<Message> handleUnsupportedOperationException(NotSupportedFormatException e){
        return ResponseEntity.badRequest().body(new Message("Upload file not .txt format."));
    }

    @ExceptionHandler(WrongNestingSectionException.class)
    public ResponseEntity<Message> handleWrongNestingSectionException(WrongNestingSectionException e) {
        return ResponseEntity.badRequest().body(new Message("Wrong nesting of sections in file."));
    }
}