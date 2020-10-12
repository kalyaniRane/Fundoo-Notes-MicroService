package com.bridgelabz.noteservice.controller;

import com.bridgelabz.noteservice.dto.NoteDTO;
import com.bridgelabz.noteservice.dto.ResponseDTO;
import com.bridgelabz.noteservice.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    INoteService noteService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createNote (@Valid @RequestBody NoteDTO noteDTO, @RequestHeader(value = "token",required = false) String token, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        System.out.println(token);
        String note = noteService.createNote(noteDTO,token);
        ResponseDTO responseDTO=new ResponseDTO(note,200);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDTO> updateNote (@RequestParam(name = "ID") Integer ID, @RequestBody NoteDTO noteDTO,@RequestHeader(value = "token",required = false) String token,HttpServletRequest request) throws IOException {
        String note = noteService.updateNote(ID,noteDTO,token);
        ResponseDTO responseDTO=new ResponseDTO(note,200);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PostMapping("/archive")
    public ResponseEntity<ResponseDTO> archieveNotes(@RequestParam(value = "noteID") Integer noteID,@RequestHeader(value = "token",required = false) String token,HttpServletRequest request){
        String note = noteService.archiveNote(noteID, token);
        ResponseDTO responseDTO=new ResponseDTO(note,200);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<ResponseDTO> trashNote (@RequestParam(value = "noteID") Integer noteID,@RequestHeader(value = "token",required = false) String token){

        String message = noteService.trashNote(noteID,token);
        ResponseDTO responseDTO=new ResponseDTO(message,200);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);

    }

    @DeleteMapping("/trash")
    public ResponseEntity<ResponseDTO> deleteNote (@RequestParam(value = "noteID") Integer noteID,@RequestHeader(value = "token",required = false) String token){
        String message=noteService.deleteNote(noteID,token);
        ResponseDTO responseDTO=new ResponseDTO(message,200);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PutMapping("/set/color")
    public ResponseEntity<ResponseDTO> setColorToNote (@RequestParam(value = "noteID") Integer noteID,@RequestParam(value = "color") String color,@RequestHeader(value = "token",required = false) String token){
        String message = noteService.setColor(color,noteID,token);
        ResponseDTO responseDTO=new ResponseDTO(message,200);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

}
