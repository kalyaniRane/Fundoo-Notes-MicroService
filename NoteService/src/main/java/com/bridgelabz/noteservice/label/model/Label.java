package com.bridgelabz.noteservice.label.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @Column(nullable = false)
    private String labelName;

    @Column(nullable = false)
    private LocalDateTime created=LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modified=LocalDateTime.now();

    @Column(nullable = false)
    private Integer userID;

}
