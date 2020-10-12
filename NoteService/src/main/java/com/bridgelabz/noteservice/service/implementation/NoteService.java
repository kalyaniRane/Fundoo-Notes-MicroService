package com.bridgelabz.noteservice.service.implementation;

import com.bridgelabz.noteservice.dto.NoteDTO;
import com.bridgelabz.noteservice.exception.NoteServiceException;
import com.bridgelabz.noteservice.model.Note;
import com.bridgelabz.noteservice.repository.INoteRepository;
import com.bridgelabz.noteservice.service.INoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class NoteService implements INoteService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    INoteRepository noteRepository;

    @Override
    public String createNote(NoteDTO noteDTO, String token) {
        Note noteDetails=new Note();
        BeanUtils.copyProperties(noteDTO,noteDetails);
//        Integer userID = restTemplate.getForObject("http://localhost:8080/user?token={token}",Integer.class,token);
        Integer userID = getUser(token);
        noteDetails.setUserID(userID);
        noteRepository.save(noteDetails);
        return "NEW NOTE CREATE";
    }

    @Override
    public String updateNote(Integer ID, NoteDTO noteDTO, String token){
        Note noteDetails = getNotesByID(ID);
        if(!noteDetails.isTrash()){
            //Integer userID = restTemplate.getForObject("http://localhost:8080/user?token={token}",Integer.class,token);
            Integer userID = getUser(token);
            if(noteDetails.getUserID().equals(userID)) {
                noteDetails.setTitle(noteDTO.title);
                noteDetails.setDescription(noteDTO.description);
                noteDetails.setModified(LocalDateTime.now());
                noteRepository.save(noteDetails);
                return "Note Updated Successful";
            }
            throw new NoteServiceException("You Can't Access This Note");
        }
        throw new NoteServiceException("Can't Edit In Trash");
    }

    @Override
    public String trashNote(Integer noteID, String token) {
        Note noteDetails = getNotesByID(noteID);
        Integer userID = getUser(token);
        if(noteDetails.getUserID().equals(userID)) {
            noteDetails.setTrash(true);
            noteRepository.save(noteDetails);
            return "Note Added In Trash";
        }
        throw new NoteServiceException("You Can't Access This Note");
    }

    @Override
    public String deleteNote(Integer noteID, String token) {
        Note noteDetails = getNotesByID(noteID);
        if(noteDetails.isTrash()){
            Integer userID = getUser(token);
            if(noteDetails.getUserID().equals(userID)) {
                noteRepository.delete(noteDetails);
                return "Note Deleted Successfully";
            }
            throw new NoteServiceException("You Can't Access This Note");
        }
        throw new NoteServiceException("Note is Not in trash");
    }

    /*@Override
    public List<NoteDetails> getAllNotes() {
        return null;
    }

    @Override
    public List<NoteDetails> getAllNotesOfTrash() {
        return null;
    }*/

    @Override
    public String archiveNote(Integer noteID, String token) {
        Note noteDetails = getNotesByID(noteID);
        if(!noteDetails.isTrash()){
            //Integer userID = restTemplate.getForObject("http://localhost:8080/user?token={token}",Integer.class,token);
            Integer userID = getUser(token);
            if(noteDetails.getUserID().equals(userID)) {
                noteDetails.setArchive(true);
                noteRepository.save(noteDetails);
                return "Note Archive";
            }
            throw new NoteServiceException("You Can't Access This Note");
        }
        throw new NoteServiceException("Can't Archive In Trash");
    }

    @Override
    public String setColor(String color, Integer noteID, String token) {
        Note noteDetails = getNotesByID(noteID);
        if(!noteDetails.isTrash()){
            //Integer userID = restTemplate.getForObject("http://localhost:8080/user?token={token}",Integer.class,token);
            Integer userID = getUser(token);
            if(noteDetails.getUserID().equals(userID)) {
                noteDetails.setColor(color);
                noteRepository.save(noteDetails);
                return "Color Set For Note";
            }
            throw new NoteServiceException("You Can't Access This Note");
        }
        throw new NoteServiceException("Can't Set Color In Trash");
    }

   /* @Override
    public String unarchiveNote(Integer noteID) {
        return null;
    }

    @Override
    public List<NoteDetails> getAllNotesOfArchive() {
        return null;
    }*/

    public Integer getUser(String token){
        return restTemplate.getForObject("http://localhost:8080/user?token={token}",Integer.class,token);
    }

    public Note getNotesByID(Integer noteID){
        Note noteDetails = noteRepository.findById(noteID).orElseThrow(() -> new NoteServiceException("Note Not Found"));
        return noteDetails;
    }

}
