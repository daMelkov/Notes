package com.astra.notes.security;

import android.app.Activity;
import android.content.Context;

public class Security extends Activity /*implements Keystore*/ {
    private static Security instance;
    private Keystore keyStore;

    //@Override
    public static boolean hasPin() {
        return getInstance().keyStore.hasPin();
    }

    //@Override
    public static boolean checkPin(String pin) {
        return getInstance().keyStore.checkPin(pin);
    }

    //@Override
    public static void saveNew(String pin) {
        getInstance().keyStore.saveNew(pin);
    }

    //@Override
    public static void removePin() {
        getInstance().keyStore.removePin();
    }

    public static void setContext(Context context) {
        getInstance().keyStore = new HashedKeystore(context);
    }

    private static Security getInstance() {
        if(instance == null) {
            instance = new Security();
        }
        return instance;
    }

    private Security() {
    }
}