package com.bridgelabz.noteservice.label.controller;

import com.bridgelabz.noteservice.dto.ResponseDTO;
import com.bridgelabz.noteservice.label.dto.LabelDTO;
import com.bridgelabz.noteservice.label.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    ILabelService labelService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createLabel (@Valid @RequestBody LabelDTO labelDTO, @RequestHeader(value = "token",required = false) String token, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        String label = labelService.createLabel(labelDTO, token);
        ResponseDTO responseDTO=new ResponseDTO(label,200);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseDTO> updateLabel (@RequestParam String labelName,@RequestParam Integer labelID,@RequestHeader(value = "token",required = false) String token){
        String note = labelService.updateLabel(labelName,labelID);
        ResponseDTO responseDTO=new ResponseDTO(note,200);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

}
