package com.example.unitconverterapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner sourceSpinner, destSpinner;
    EditText inputValue;
    Button convertButton;
    TextView resultText;

    String[] units = {
            "Inch", "Foot", "Yard", "Mile",
            "Pound", "Ounce", "Ton",
            "Celsius", "Fahrenheit", "Kelvin"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        sourceSpinner = findViewById(R.id.sourceUnitSpinner);
        destSpinner = findViewById(R.id.destinationUnitSpinner);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter);
        destSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(v -> convertUnits());
    }

    private void convertUnits() {
        String from = sourceSpinner.getSelectedItem().toString();
        String to = destSpinner.getSelectedItem().toString();
        double input;

        try {
            input = Double.parseDouble(inputValue.getText().toString());
        } catch (Exception e) {
            resultText.setText("Enter a valid number");
            return;
        }

        double result = performConversion(from, to, input);
        resultText.setText("Result: " + result);
    }

    private double performConversion(String from, String to, double value) {
        if (from.equals(to)) return value;

        // Convert to base
        if (from.equals("Inch")) value *= 2.54;
        else if (from.equals("Foot")) value *= 30.48;
        else if (from.equals("Yard")) value *= 91.44;
        else if (from.equals("Mile")) value *= 160934;
        else if (from.equals("Pound")) value *= 0.453592;
        else if (from.equals("Ounce")) value *= 0.0283495;
        else if (from.equals("Ton")) value *= 907.185;
        else if (from.equals("Celsius") && to.equals("Fahrenheit")) return (value * 1.8) + 32;
        else if (from.equals("Fahrenheit") && to.equals("Celsius")) return (value - 32) / 1.8;
        else if (from.equals("Celsius") && to.equals("Kelvin")) return value + 273.15;
        else if (from.equals("Kelvin") && to.equals("Celsius")) return value - 273.15;
        else if (from.equals("Fahrenheit") && to.equals("Kelvin")) return ((value - 32) / 1.8) + 273.15;
        else if (from.equals("Kelvin") && to.equals("Fahrenheit")) return ((value - 273.15) * 1.8) + 32;

        // Convert from base
        if (to.equals("Inch")) return value / 2.54;
        else if (to.equals("Foot")) return value / 30.48;
        else if (to.equals("Yard")) return value / 91.44;
        else if (to.equals("Mile")) return value / 160934;
        else if (to.equals("Pound")) return value / 0.453592;
        else if (to.equals("Ounce")) return value / 0.0283495;
        else if (to.equals("Ton")) return value / 907.185;

        return 0;
    }
}
