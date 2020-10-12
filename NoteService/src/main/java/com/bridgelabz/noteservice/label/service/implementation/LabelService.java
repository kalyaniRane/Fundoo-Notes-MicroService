package com.bridgelabz.noteservice.label.service.implementation;

import com.bridgelabz.noteservice.exception.LabelServiceException;
import com.bridgelabz.noteservice.label.dto.LabelDTO;
import com.bridgelabz.noteservice.label.model.Label;
import com.bridgelabz.noteservice.label.repository.ILabelRepository;
import com.bridgelabz.noteservice.label.service.ILabelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class LabelService implements ILabelService {

    @Autowired
    ILabelRepository labelRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String createLabel(LabelDTO labelDTO, String token) {
        Label label=new Label();
        BeanUtils.copyProperties(labelDTO,label);
        Integer userID = getUser(token);
        label.setUserID(userID);
        labelRepository.save(label);
        return "LABEL CREATED";

    }

    @Override
    public String updateLabel(String labelName, Integer labelID) {
        Label label = labelRepository.findById(labelID).orElseThrow(() -> new LabelServiceException("Label Not Found"));
        label.setLabelName(labelName);
        label.setModified(LocalDateTime.now());
        labelRepository.save(label);
        return "Label Updated Successful";
    }

    public Integer getUser(String token){
        return restTemplate.getForObject("http://localhost:8080/user?token={token}",Integer.class,token);
    }

}
