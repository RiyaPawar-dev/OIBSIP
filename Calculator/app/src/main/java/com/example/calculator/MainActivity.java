package com.example.calculator;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private GridLayout buttonGrid;

    private double previousValue = 0;
    private String operator = "";
    private boolean waitingForOperand = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        buttonGrid = findViewById(R.id.calculatorCard).findViewById(R.id.buttonGrid);

        // Add click listeners to all buttons
        for (int i = 0; i < buttonGrid.getChildCount(); i++) {
            View child = buttonGrid.getChildAt(i);
            if (child instanceof Button) {
                child.setOnClickListener(v -> handleButtonClick(((Button) v).getText().toString()));
            }
        }
    }

    private void handleButtonClick(String value) {
        switch (value) {
            case "C":
                clear();
                break;
            case "±":
                toggleSign();
                break;
            case "%":
                percentage();
                break;
            case "+":
            case "-":
            case "×":
            case "÷":
                setOperator(value);
                break;
            case "=":
                calculateResult();
                break;
            case ".":
                inputDecimal();
                break;
            default: // Numbers
                inputDigit(value);
                break;
        }
    }

    private void inputDigit(String digit) {
        if (waitingForOperand) {
            display.setText(digit);
            waitingForOperand = false;
        } else {
            if (display.getText().toString().equals("0")) {
                display.setText(digit);
            } else {
                display.append(digit);
            }
        }
    }

    private void inputDecimal() {
        if (waitingForOperand) {
            display.setText("0.");
            waitingForOperand = false;
        } else if (!display.getText().toString().contains(".")) {
            display.append(".");
        }
    }

    private void clear() {
        display.setText("0");
        previousValue = 0;
        operator = "";
        waitingForOperand = false;
    }

    private void toggleSign() {
        String text = display.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            double val = Double.parseDouble(text) * -1;
            display.setText(String.valueOf(val));
        }
    }

    private void percentage() {
        String text = display.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            double val = Double.parseDouble(text) / 100;
            display.setText(String.valueOf(val));
        }
    }

    private void setOperator(String op) {
        String text = display.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            previousValue = Double.parseDouble(text);
            operator = op;
            waitingForOperand = true;
        }
    }

    private void calculateResult() {
        String text = display.getText().toString();
        if (!TextUtils.isEmpty(text) && !operator.isEmpty()) {
            double currentValue = Double.parseDouble(text);
            double result = previousValue;

            switch (operator) {
                case "+":
                    result += currentValue;
                    break;
                case "-":
                    result -= currentValue;
                    break;
                case "×":
                    result *= currentValue;
                    break;
                case "÷":
                    if (currentValue != 0) {
                        result /= currentValue;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }

            display.setText(String.valueOf(result));
            previousValue = result;
            waitingForOperand = true;
            operator = "";
        }
    }
}
