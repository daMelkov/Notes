package com.astra.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.astra.notes.note.Note;
import com.astra.notes.note.NoteActivity;
import com.astra.notes.security.Security;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int PIN = 100;
    private static final int NEED_REFRESH = 200;
    private Db db;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PIN) {
            Log.i("PIN_ACTIVITY", "pin requested");

            String pin = data.getStringExtra("pin_code");
            if(!Security.checkPin(pin)) {
                Toast.makeText(MainActivity.this, "Wrong pin!", Toast.LENGTH_SHORT).show();
                checkPin();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Security.setContext(this);
        if(Security.hasPin()) {
            checkPin();
        }

        setContentView(R.layout.activity_main);

        initViews();
    }

    private void checkPin() {
        Intent intent = new Intent(MainActivity.this, PinActivity.class);
        startActivityForResult(intent, PIN);
    }

    private void initViews() {
        /* ToolBar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* SwipeRefreshLayout */
        final SwipeRefreshLayout swipe = findViewById(R.id.main_swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //content.clear();
                //content.addAll(prepareContent());
                //adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        });

        /* ListView */
        final List<Map<String, String>> content = getContent();
        final BaseAdapter adapter = createAdapter(content);

        final ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);

        /* FAB */
        FloatingActionButton btnAdd = findViewById(R.id.fab_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivityForResult(intent, NEED_REFRESH);
            }
        });
    }

    private BaseAdapter createAdapter(List<Map<String, String>> content) {
        String[] from = new String[] {
                "header",
                "content",
                "deadline"
        };

        int[] to = new int[] {
                R.id.txt_header,
                R.id.txt_content,
                R.id.txt_deadline
        };

        return new SimpleAdapter(this, content, R.layout.list_item, from, to);
    }

    private List<Map<String, String>> getContent() {
        db = Db.getInstance(MainActivity.this);

        List<Map<String, String>> list = new ArrayList<>();

        List<Note> notes = db.noteDao().getNoteList();
        for(Note note : notes) {
            Map<String, String> item = new HashMap<>();

            item.put("id", String.valueOf(note.getId()));
            item.put("header", note.getTitle());
            item.put("context", note.getDescription());
            item.put("deadline", note.getDate().toString());

            list.add(item);
        }

//        Map<String, String> item1 = new HashMap<>();
//        item1.put("header", "header");
//        item1.put("content", "content");
//        item1.put("deadline","2020-09-18");
//        list.add(item1);
//
//        Map<String, String> item2 = new HashMap<>();
//        item2.put("content", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                "Nullam ut porttitor libero. Aenean in sem orci. Quisque fermentum ornare bibendum. " +
//                "Proin ultricies, turpis non maximus imperdiet, lectus nulla efficitur justo, " +
//                "a faucibus neque ligula vel nulla. Pellentesque vel arcu eleifend, " +
//                "feugiat mauris a, pretium turpis.");
//        item2.put("deadline","2020-09-18");
//        list.add(item2);

        return list;
    }
}