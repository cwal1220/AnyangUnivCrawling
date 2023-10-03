package com.example.anyang_setup;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PersonalWriteActivity extends AppCompatActivity {

    Spinner questionSpinner;
    EditText editText;
    Button resetButton;
    Button saveButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_write);

        questionSpinner = findViewById(R.id.personal_question);
        editText = findViewById(R.id.personal_text);
        resetButton = findViewById(R.id.personal_write_reset_button);
        saveButton = findViewById(R.id.personal_write_save_button);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                questionSpinner.setSelection(0);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedQuestion = questionSpinner.getSelectedItem().toString();
                String userText = editText.getText().toString();

                // Intent를 생성하여 PersonalLockerActivity로 전달
                Intent intent = new Intent(PersonalWriteActivity.this, PersonalLockerActivity.class);

                // 데이터를 intent에 담아 전달
                intent.putExtra("selectedQuestion", selectedQuestion);
                intent.putExtra("userText", userText);

                startActivity(intent);
            }
        });
    }
}
