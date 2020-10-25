package com.astra.notes.repository;

import com.astra.notes.note.Note;

import java.util.List;

public class SqliteRepository implements Repository {
    @Override
    public Note getNoteById(String id) {
        return null;
    }

    @Override
    public List<Note> getNotes() {
        return null;
    }

    @Override
    public void saveNote(Note note) {

    }

    @Override
    public void deleteById(String id) {

    }
}
