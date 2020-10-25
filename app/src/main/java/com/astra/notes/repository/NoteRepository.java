package com.astra.notes.repository;

import com.astra.notes.note.Note;

import java.util.List;


public class NoteRepository implements Repository {
    private static NoteRepository instance;
    private Repository repository;

    public NoteRepository getInstance() {
        if(instance == null) {
            instance = new NoteRepository();
        }

        return instance;
    }

    private NoteRepository() {
        repository = new SqliteRepository();
    }

    @Override
    public Note getNoteById(String id) {
        return repository.getNoteById(id);
    }

    @Override
    public List<Note> getNotes() {
        return repository.getNotes();
    }

    @Override
    public void saveNote(Note note) {
        repository.saveNote(note);
    }

    @Override
    public void deleteById(String id) {
        repository.getNoteById(id);
    }
}
