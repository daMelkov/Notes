package com.astra.notes.security;

interface Keystore {
    boolean hasPin();
    boolean checkPin(String pin);
    void saveNew(String pin);
    void removePin();
}
