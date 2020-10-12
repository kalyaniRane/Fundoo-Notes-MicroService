package com.bridgelabz.noteservice.label.repository;


import com.bridgelabz.noteservice.label.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILabelRepository extends JpaRepository<Label,Integer> {
   /* List<LabelDetails> findAllByUser(UserDetails userDetails);

    Optional<LabelDetails> findByLabelName(String labelName);*/
}
