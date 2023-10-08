package com.example.anyang_setup.Info.SubActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.anyang_setup.R;

public class DiagnosisActivity extends AppCompatActivity {

    private static final String TAG = "DiagnosisActivity";
    private TextView 교양필수TextView;
    private TextView 교양선택TextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        교양필수TextView = findViewById(R.id.General_Education_Requirement);
        교양선택TextView = findViewById(R.id.General_Elective);

        Intent intent = getIntent();
        if (intent != null) {
            String ID = intent.getStringExtra("ID");
            String password = intent.getStringExtra("password");

            new DiagnosisRequest(this, ID, password).execute();
        }
    }

    public void setDataFromServer(String 교양필수, String 교양선택) {
        교양필수TextView.setText("교양필수: " + 교양필수);
        교양선택TextView.setText("교양선택: " + 교양선택);
    }
}
