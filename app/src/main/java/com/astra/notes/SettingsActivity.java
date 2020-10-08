package com.astra.notes;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private static final int PIN_CODE = 100;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swtPinStatus;
    private TextView txtPinStatus;

    private static Keystore keyStore;
    private static Context context;

    /**
     * Получить политику хранения пин-кода
     * @return политика хранения пин-кода
     */
    public static Keystore getKeyStore() {
        Context context = getContext();

        if(keyStore == null) {
            keyStore = new HashedKeystore(context);
        }

        return keyStore;
    }

    private static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        context = getApplicationContext();

        initViews();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String code = data.getStringExtra("pin_code");
            getKeyStore().saveNew(code);
        }

        setPinState();
    }

    private void initViews() {
        // Switch: change pin-code status
        swtPinStatus = findViewById(R.id.switch_pin_status);
        swtPinStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked ^ getKeyStore().hasPin()) {
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
            // set pin
            Intent intent = new Intent(SettingsActivity.this, PinActivity.class);
            startActivityForResult(intent, 0);
        } else {
            // remove pin
            getKeyStore().saveNew("");
        }
    }

    private void setPinState() {
        if(getKeyStore().hasPin()) {
            txtPinStatus.setText(getString(R.string.pin_exists));
            swtPinStatus.setChecked(true);
        } else {
            txtPinStatus.setText(getString(R.string.pin_not_exists));
            swtPinStatus.setChecked(false);
        }
    }
}
