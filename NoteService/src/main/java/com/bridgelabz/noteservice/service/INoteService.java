package com.bridgelabz.noteservice.service;


import com.bridgelabz.noteservice.dto.NoteDTO;

import java.io.IOException;

public interface INoteService {

    String createNote(NoteDTO noteDTO, String token);

    String updateNote(Integer ID, NoteDTO noteDTO, String token) throws IOException;

    String trashNote(Integer noteI, String token);

    String deleteNote(Integer noteID, String token);

   /* List<NoteDetails> getAllNotes();

    List<NoteDetails> getAllNotesOfTrash();*/

    String archiveNote(Integer noteID, String token);

    /*String unarchiveNote(Integer noteID);

    List<NoteDetails> getAllNotesOfArchive();*/

    String setColor (String color,Integer noteID,String token);

}

