package com.astra.notes.note;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.astra.notes.R;
import com.astra.notes.security.Security;

public class NoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
    }
}