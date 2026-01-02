package com.example.unitconversion;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputValue;
    Spinner fromUnit, toUnit;
    ImageButton btnSwap;
    TextView resultText;

    // Category TextViews
    TextView len, weight, temp, speed;

    // Unit arrays
    String[] lengthUnits = {"Meter", "Kilometer", "Centimeter"};
    String[] weightUnits = {"Kilogram", "Gram"};
    String[] tempUnits = {"Celsius", "Fahrenheit"};
    String[] speedUnits = {"Meter/Second", "Kilometer/Second"};

    String currentCategory = "LENGTH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind views
        inputValue = findViewById(R.id.inputValue);
        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        btnSwap = findViewById(R.id.btnSwap);
        resultText = findViewById(R.id.resultText);

        len = findViewById(R.id.len);
        weight = findViewById(R.id.weight);
        temp = findViewById(R.id.temp);
        speed = findViewById(R.id.speed);

        // Default category
        setCategory("LENGTH");

        // Category clicks
        len.setOnClickListener(v -> setCategory("LENGTH"));
        weight.setOnClickListener(v -> setCategory("WEIGHT"));
        temp.setOnClickListener(v -> setCategory("TEMP"));
        speed.setOnClickListener(v -> setCategory("SPEED"));

        // Auto convert while typing
        inputValue.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                convert();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Convert when spinner changes
        fromUnit.setOnItemSelectedListener(new SimpleListener(this::convert));
        toUnit.setOnItemSelectedListener(new SimpleListener(this::convert));

        // Swap units
        btnSwap.setOnClickListener(v -> swapUnits());
    }

    // Load units based on category
    private void setCategory(String category) {
        currentCategory = category;

        String[] units;
        int fromPos = 0;
        int toPos = 1;

        switch (category) {
            case "WEIGHT":
                units = weightUnits;
                break;
            case "TEMP":
                units = tempUnits;
                break;
            case "SPEED":
                units = speedUnits;
                break;
            default:
                units = lengthUnits;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                units
        );

        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);

        fromUnit.setSelection(fromPos);
        toUnit.setSelection(toPos);

        inputValue.setText("");
        resultText.setText("0");
    }

    // Conversion logic
    private void convert() {
        String input = inputValue.getText().toString().trim();
        if (input.isEmpty()) {
            resultText.setText("0");
            return;
        }

        double value = Double.parseDouble(input);
        String from = fromUnit.getSelectedItem().toString();
        String to = toUnit.getSelectedItem().toString();
        double result = value;

        switch (currentCategory) {

            case "LENGTH":
                if (from.equals("Meter") && to.equals("Kilometer")) result = value / 1000;
                else if (from.equals("Kilometer") && to.equals("Meter")) result = value * 1000;
                else if (from.equals("Meter") && to.equals("Centimeter")) result = value * 100;
                else if (from.equals("Centimeter") && to.equals("Meter")) result = value / 100;
                break;

            case "WEIGHT":
                if (from.equals("Kilogram") && to.equals("Gram")) result = value * 1000;
                else if (from.equals("Gram") && to.equals("Kilogram")) result = value / 1000;
                break;

            case "TEMP":
                if (from.equals("Celsius") && to.equals("Fahrenheit"))
                    result = (value * 9 / 5) + 32;
                else if (from.equals("Fahrenheit") && to.equals("Celsius"))
                    result = (value - 32) * 5 / 9;
                break;

            case "SPEED":
                if (from.equals("Meter/Second") && to.equals("Kilometer/Second"))
                    result = value / 1000;
                else if (from.equals("Kilometer/Second") && to.equals("Meter/Second"))
                    result = value * 1000;
                break;
        }

        resultText.setText(String.valueOf(result));
    }

    private void swapUnits() {
        int fromPos = fromUnit.getSelectedItemPosition();
        int toPos = toUnit.getSelectedItemPosition();

        fromUnit.setSelection(toPos);
        toUnit.setSelection(fromPos);
    }
}
