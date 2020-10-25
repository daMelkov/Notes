package com.astra.notes.repository;

import com.astra.notes.note.Note;

import java.util.List;

public interface Repository {
    Note getNoteById(String id);
    List<Note> getNotes();
    void saveNote(Note note);
    void deleteById(String id);
}