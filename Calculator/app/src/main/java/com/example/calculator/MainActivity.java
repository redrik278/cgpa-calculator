package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculator.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Float> creditsList = new ArrayList<>();
    private List<Float> gradesList = new ArrayList<>();

    private TextView textViewResult;
    private EditText editTextCredits;
    private Spinner spinnerGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);
        editTextCredits = findViewById(R.id.editTextCredits);
        spinnerGrade = findViewById(R.id.spinnerGrade);

        // Populate the spinner with grades
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrade.setAdapter(adapter);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse();
            }
        });

        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCGPA();
            }
        });
    }

    private void addCourse() {
        String creditsString = editTextCredits.getText().toString();
        if (!creditsString.isEmpty()) {
            float credits = Float.parseFloat(creditsString);
            creditsList.add(credits);

            String gradeString = spinnerGrade.getSelectedItem().toString();
            float grade = getGradeValue(gradeString);
            gradesList.add(grade);

            editTextCredits.getText().clear();
            textViewResult.setText(""); // Clear previous result
        }
    }

    private float getGradeValue(String grade) {
        // You can define your own grade-to-value mapping here
        // For simplicity, I'm using a basic mapping where A=4, B=3, C=2, D=1, F=0
        switch (grade) {
            case "A+": return 4.00f;
            case "A": return 4.00f;
            case "A-": return 3.70f;
            case "B+": return 3.30f;
            case "B": return 3.00f;
            case "B-": return 2.70f;
            case "C+": return 2.30f;
            case "C": return 2.00f;
            case "C-": return 1.70f;
            case "D+": return 1.30f;
            case "D": return 1.00f;
            default: return 0.00f;
        }
    }

    private void calculateCGPA() {
        float totalCredits = 0;
        float totalGradePoints = 0;

        for (int i = 0; i < creditsList.size(); i++) {
            float credits = creditsList.get(i);
            float grade = gradesList.get(i);

            totalCredits += credits;
            totalGradePoints += credits * grade;
        }

        float cgpa = totalGradePoints / totalCredits;
        DecimalFormat df = new DecimalFormat("#.##");
        textViewResult.setText("CGPA: " + df.format(cgpa));
    }
}
