package com.bridgelabz.noteservice.exception;


import com.bridgelabz.noteservice.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FundooNotesExceptionHandler {

    @ExceptionHandler(NoteServiceException.class)
    public ResponseEntity noteServiceExceptionHandler(NoteServiceException e){
        ResponseDTO responseDTO = new ResponseDTO(e.getMessage(),500);
        return new ResponseEntity(responseDTO, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(LabelServiceException.class)
    public ResponseEntity labelServiceExceptionHandler(LabelServiceException e){
        ResponseDTO responseDTO = new ResponseDTO(e.getMessage(),500);
        return new ResponseEntity(responseDTO,HttpStatus.ALREADY_REPORTED);
    }

}
