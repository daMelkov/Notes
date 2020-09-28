package com.astra.notes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class PinActivity extends AppCompatActivity {
    private static final int PINS_COUNT = 4;
    private static final int[] PINS_MARKERS = new int[] {
            R.id.txt_round_1,
            R.id.txt_round_2,
            R.id.txt_round_3,
            R.id.txt_round_4
    };

    private Stack<Integer> PINS = new Stack<>();
    private int currentPin = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        initViews();
    }

    private void initViews() {
        Button btnOne = findViewById(R.id.button_one);
        btnOne.setOnClickListener(onClick);

        Button btnTwo = findViewById(R.id.button_two);
        btnTwo.setOnClickListener(onClick);

        Button btnThree = findViewById(R.id.button_three);
        btnThree.setOnClickListener(onClick);

        Button btnFour = findViewById(R.id.button_four);
        btnFour.setOnClickListener(onClick);

        Button btnFive = findViewById(R.id.button_five);
        btnFive.setOnClickListener(onClick);

        Button btnSix = findViewById(R.id.button_six);
        btnSix.setOnClickListener(onClick);

        Button btnSeven = findViewById(R.id.button_seven);
        btnSeven.setOnClickListener(onClick);

        Button btnEight = findViewById(R.id.button_eight);
        btnEight.setOnClickListener(onClick);

        Button btnNine = findViewById(R.id.button_nine);
        btnNine.setOnClickListener(onClick);

        Button btnZero = findViewById(R.id.button_zero);
        btnZero.setOnClickListener(onClick);

        Button btnDelete = findViewById(R.id.button_delete);
        btnDelete.setOnClickListener(onDelete);

        Button btnCancel = findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PINS.size();
            }
        });
    }

    /* 0...9 */
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int number = Integer.parseInt(String.valueOf(((Button) view).getText()));

            if(currentPin == PINS_COUNT) {
                return;
            }

            PINS.add(number);
            firePoint(currentPin);

            currentPin++;
            if(currentPin == PINS_COUNT) {
                setPin(PINS);
            }
        }
    };

    /* delete */
    View.OnClickListener onDelete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(currentPin == 0) {
                return;
            }

            PINS.pop();
            currentPin--;
            firePoint(currentPin);
        }
    };

    private void firePoint(int currentPin) {
        TextView view = (TextView) findViewById(PINS_MARKERS[currentPin]);
        view.setBackgroundResource(R.drawable.filled_pin);
    }

    private void setPin(Stack<Integer> pins) {
        Integer[] numbers = new Integer[PINS_COUNT];
        pins.toArray(numbers);

        StringBuilder builder = new StringBuilder();
        for(Integer number : numbers) {
            builder.append(number);
        }

        Integer code = Integer.parseInt(builder.toString());
        finish();
    }
}