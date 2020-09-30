package com.astra.notes;

interface Keystore {
    boolean hasPin();
    boolean checkPin(int pin);
    void saveNew(int pin);
}
