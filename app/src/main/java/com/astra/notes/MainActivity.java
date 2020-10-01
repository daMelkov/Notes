package com.astra.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int PIN = 100;

    private SharedPreferences preferences;
    private Keystore keyStore;

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
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPreferences();
        initViews();
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
    }

    private void initPreferences() {


        keyStore = new HashedKeystore(MainActivity.this);
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
        List<Map<String, String>> list = new ArrayList<>();

        Map<String, String> item1 = new HashMap<>();
        item1.put("header", "header");
        item1.put("content", "content");
        item1.put("deadline","2020-09-18");
        list.add(item1);

        Map<String, String> item2 = new HashMap<>();
        //item2.put("header", "header");
        item2.put("content", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Nullam ut porttitor libero. Aenean in sem orci. Quisque fermentum ornare bibendum. " +
                "Proin ultricies, turpis non maximus imperdiet, lectus nulla efficitur justo, " +
                "a faucibus neque ligula vel nulla. Pellentesque vel arcu eleifend, " +
                "feugiat mauris a, pretium turpis.");
        item2.put("deadline","2020-09-18");
        list.add(item2);

        return list;
    }
}