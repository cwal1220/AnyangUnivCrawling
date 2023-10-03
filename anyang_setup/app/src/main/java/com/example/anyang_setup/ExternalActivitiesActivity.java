package com.example.anyang_setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ExternalActivitiesActivity extends AppCompatActivity {

    private EditText externalActivitiesText;
    private Button saveButton;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.external_activities_write);

        externalActivitiesText = findViewById(R.id.external_activities_text);
        saveButton = findViewById(R.id.external_activities_write_save_button);
        resetButton = findViewById(R.id.external_activities_write_reset_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 텍스트를 가져옵니다.
                String externalActivities = externalActivitiesText.getText().toString();

                // 인텐트를 생성하여 데이터를 SpecActivity로 전달합니다.
                Intent intent = new Intent();
                intent.putExtra("externalActivities", externalActivities);
                setResult(RESULT_OK, intent);

                // ExternalActivitiesActivity를 종료합니다.
                finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력한 텍스트를 초기화합니다.
                externalActivitiesText.setText("");
            }
        });
    }
}
