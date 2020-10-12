package com.bridgelabz.noteservice.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime created=LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modified=LocalDateTime.now();

    @Column(nullable = false)
    private boolean isTrash;

    @Column(nullable = false)
    private boolean isArchive;

    @Column(nullable = false)
    private Integer userID;

    @Column
    private String color;

}

