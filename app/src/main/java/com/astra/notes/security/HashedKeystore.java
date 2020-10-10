package com.astra.notes.security;

import android.content.Context;
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

    private static String mSalt;
    private static String mHashCode;
    private SharedPreferences preferences;
    private static SecretKeyFactory factory;

    public HashedKeystore(Context context) {
        this.preferences = context.getSharedPreferences("PIN", MODE_PRIVATE);

        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            Log.e("PIN_CODE", "pin-code factory error");
        }

        mSalt = preferences.getString(SALT, "");
        mHashCode = preferences.getString(HASH, "");
    }

    @Override
    public boolean hasPin() {
        return mHashCode.length() != 0 && mSalt.length() != 0;
    }

    @Override
    public boolean checkPin(String pin) {
        String hash = generateHash(pin, mSalt);

        return Arrays.equals(hash.getBytes(), mHashCode.getBytes());
    }

    @Override
    public void saveNew(String pin) {
        mSalt = generateSalt();
        mHashCode = generateHash(pin, mSalt);
        writePin(mHashCode, mSalt);
    }

    @Override
    public void removePin() {
        mHashCode = "";
        mSalt = "";
        writePin(mHashCode, mSalt);
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);

        return new String(saltBytes, StandardCharsets.UTF_8);
    }

    private String generateHash(String pin, String salt) {
        KeySpec spec = new PBEKeySpec(pin.toCharArray(), salt.getBytes(), 65536, 128);
        try {
            byte[] hashBytes = factory.generateSecret(spec).getEncoded();
            return new String(hashBytes, StandardCharsets.UTF_8);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void writePin(String hashCode, String salt) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SALT, salt);
        editor.putString(HASH, hashCode);
        editor.apply();
    }
}