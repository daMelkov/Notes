package com.astra.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        /* ListView */
        final List<Map<String, String>> content = getContent();
        final BaseAdapter adapter = createAdapter(content);

        final ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);
    }

    private BaseAdapter createAdapter(List<Map<String, String>> content) {
        String[] from = new String[]{"header","content", "deadline"};
        int[] to = new int[] {R.id.txt_header, R.id.txt_content, R.id.txt_deadline};

        return new SimpleAdapter(this, content, R.layout.list_item, from, to);
    }

    private List<Map<String, String>> getContent() {
        List<Map<String, String>> list = new ArrayList<>();

        Map<String, String> item = new HashMap<>();
        item.put("header", "header");
        item.put("content", "content");
        item.put("deadline","2020-09-18");

        list.add(item);
        return list;
    }
}