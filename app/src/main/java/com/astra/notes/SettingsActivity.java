package com.astra.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.astra.notes.security.Security;

public class SettingsActivity extends AppCompatActivity {
    private static final int PIN_CODE = 100;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swtPinStatus;
    private TextView txtPinStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String code = data.getStringExtra("pin_code");
            Security.saveNew(code);
        }

        setPinState();
    }

    private void initViews() {
        // Switch: change pin-code status
        swtPinStatus = findViewById(R.id.switch_pin_status);
        swtPinStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked ^ Security.hasPin()) {
                    setPinState(!isChecked);
                }
            }
        });

        // Text: pin-code status
        txtPinStatus = findViewById(R.id.txt_pin_status);
        setPinState();
    }

    private void setPinState(boolean isChecked) {
        if(!isChecked) {
            // set pin (in onActivityResult)
            Intent intent = new Intent(SettingsActivity.this, PinActivity.class);
            startActivityForResult(intent, PIN_CODE);
        } else {
            // remove pin
            Security.removePin();
        }
    }

    private void setPinState() {
        if(Security.hasPin()) {
            txtPinStatus.setText(getString(R.string.pin_exists));
            swtPinStatus.setChecked(true);
        } else {
            txtPinStatus.setText(getString(R.string.pin_not_exists));
            swtPinStatus.setChecked(false);
        }
    }
}