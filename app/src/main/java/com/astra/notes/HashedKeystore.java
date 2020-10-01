package com.astra.notes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import static android.content.Context.MODE_PRIVATE;

/** Documentation: https://www.baeldung.com/java-password-hashing */
public class HashedKeystore implements Keystore {
    private static final String SALT = "salt";
    private static final String HASH = "hash";

    private String salt;
    private String hashCode;
    private SharedPreferences preferences;
    private static SecretKeyFactory factory;

    public HashedKeystore(Activity activity) {
        preferences = activity.getPreferences(MODE_PRIVATE);
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            Log.e("PIN_CODE", "pin-code factory error");
        }

        salt = preferences.getString(SALT, "");
        hashCode = preferences.getString(HASH, "");
    }

    @Override
    public boolean hasPin() {
        return hashCode.length() != 0 && salt.length() != 0;
    }

    @Override
    public boolean checkPin(String pin) {
        KeySpec spec = new PBEKeySpec(hashCode.toCharArray(), salt.getBytes(), 65536, 128);

        try {
            if(Arrays.equals(hashCode.getBytes(), factory.generateSecret(spec).getEncoded())) {
                return true;
            }
        } catch (InvalidKeySpecException e) {
            Log.e("PIN_CODE", "pin-code invalid key spec exception.");
        }

        return false;
    }

    @Override
    public void saveNew(String pin) {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);

        salt = new String(saltBytes, StandardCharsets.UTF_8);

        KeySpec spec = new PBEKeySpec(pin.toCharArray(), saltBytes, 65536, 128);
        try {
            byte[] hashBytes = factory.generateSecret(spec).getEncoded();
            hashCode = new String(hashBytes, StandardCharsets.UTF_8);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(SALT, salt);
            editor.putString(HASH, hashCode);
            editor.apply();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }
}