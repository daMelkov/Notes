package com.astra.notes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private static final int PIN_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        int code = data.getIntExtra("pin_code", 0);
        Log.i("TAG", String.valueOf(code));
        //tvName.setText("Your name is " + name);
    }

    private void initViews() {
        TextView txtEnterPin = findViewById(R.id.txt_enter_pin);
        txtEnterPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, PinActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
