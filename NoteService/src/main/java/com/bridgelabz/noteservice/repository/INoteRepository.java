package com.bridgelabz.noteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgelabz.noteservice.model.Note;


public interface INoteRepository extends JpaRepository<Note,Integer> {

}
