package com.astra.notes.note;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.astra.notes.R;

public class NoteActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText txtTitle;
    private EditText txtDescription;
    private CheckBox checkDeadline;
    private EditText txtDeadline;
    private ImageButton btnCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initViews();
    }

    private void initViews() {
        txtTitle = findViewById(R.id.edt_title);
        txtDescription = findViewById(R.id.edt_description);

        checkDeadline = findViewById(R.id.check_deadline);
        checkDeadline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                txtDeadline.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
                btnCalendar.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            }
        });

        txtDeadline = findViewById(R.id.edt_deadline_date);
        btnCalendar = findViewById(R.id.btn_select_date);
    }
}