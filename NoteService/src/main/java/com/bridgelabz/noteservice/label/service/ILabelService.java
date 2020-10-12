package com.bridgelabz.noteservice.label.service;


import com.bridgelabz.noteservice.label.dto.LabelDTO;

public interface ILabelService {

    String createLabel(LabelDTO labelDTO,String token);

    String updateLabel(String labelName, Integer labelID);

}
