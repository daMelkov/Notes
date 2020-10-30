package com.astra.notes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.astra.notes.note.Note;
import com.astra.notes.note.NoteDao;

import java.io.File;

@Database(entities = Note.class, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class Db extends RoomDatabase {
    private static final String DB_NAME = "notes.db";
    private static Db instance;

    public static synchronized Db getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Db.class, DB_NAME)
                    .createFromFile(new File(DB_NAME))
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract NoteDao noteDao();
}
