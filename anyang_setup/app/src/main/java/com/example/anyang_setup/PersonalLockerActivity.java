package com.example.anyang_setup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalLockerActivity extends AppCompatActivity {

    private EditText searchEditText;
    private GridLayout introductionGridLayout;
    private Button selectButton;
    private Button downloadButton;

    private String[] introductionList = {
            "자기소개서 1",
            "자기소개서 2",

    };

    private void filterIntroductionList(String query) {
        displayIntroductionList();
    }

    private void displayIntroductionList() {
        LayoutInflater inflater = LayoutInflater.from(this);
        introductionGridLayout.removeAllViews();

        for (int i = 0; i < introductionList.length; i++) {
            View itemView = inflater.inflate(R.layout.personal_introduction_item, introductionGridLayout, false);
            TextView titleTextView = itemView.findViewById(R.id.titleTextView);
            TextView contentTextView = itemView.findViewById(R.id.contentTextView);

            titleTextView.setText("자기소개서 " + (i + 1));

            // 여기서 선택된 질문과 사용자가 입력한 텍스트를 받아와 설정합니다.
            String selectedQuestion = getIntent().getStringExtra("selectedQuestion");
            String userText = getIntent().getStringExtra("userText");

            // 선택된 질문과 사용자가 입력한 텍스트를 contentTextView에 설정합니다.
            contentTextView.setText(selectedQuestion + ": " + userText);

            introductionGridLayout.addView(itemView);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_locker);

        searchEditText = findViewById(R.id.searchEditText);
        introductionGridLayout = findViewById(R.id.introductionGridLayout);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterIntroductionList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        displayIntroductionList();

        Intent intent = getIntent();
        String selectedQuestion = intent.getStringExtra("selectedQuestion");
        String userText = intent.getStringExtra("userText");

        if (selectedQuestion != null && userText != null) {
            Toast.makeText(this, "선택한 질문: " + selectedQuestion + "\n작성한 텍스트: " + userText, Toast.LENGTH_LONG).show();
        }
    }
}
