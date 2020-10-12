package com.bridgelabz.noteservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@AllArgsConstructor
@Getter
@Setter
public class NoteDTO {

    @NotNull(message = "Please Enter Title")
    public String title;
    @NotNull(message = "Please Enter Description")
    public String description;

}
