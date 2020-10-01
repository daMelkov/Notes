package com.astra.notes;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class InnerKeystore implements Keystore {
    private static final String SALT = "salt";
    private static final String HASH = "hash";

    private String salt;
    private String hashCode;
    private SharedPreferences preferences;

    public InnerKeystore(Activity activity) {
        preferences = activity.getPreferences(MODE_PRIVATE);

        salt = preferences.getString(SALT, "");
        hashCode = preferences.getString(HASH, "");
    }

    @Override
    public boolean hasPin() {
        return hashCode.length() != 0 && salt.length() != 0;
    }

    @Override
    public boolean checkPin(String pin) {
        return false;
    }

    @Override
    public void saveNew(String pin) {

    }
}
