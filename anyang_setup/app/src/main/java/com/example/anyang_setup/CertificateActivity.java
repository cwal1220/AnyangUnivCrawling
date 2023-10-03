package com.example.anyang_setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CertificateActivity extends AppCompatActivity {

    private EditText certificateText;
    private Button saveButton;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate_write);

        certificateText = findViewById(R.id.certificate_text);
        saveButton = findViewById(R.id.certificate_write_save_button);
        resetButton = findViewById(R.id.certificate_write_reset_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 텍스트를 가져옵니다.
                String certification = certificateText.getText().toString();

                // 인텐트를 생성하여 데이터를 SpecActivity로 전달합니다.
                Intent intent = new Intent();
                intent.putExtra("certification", certification);
                setResult(RESULT_OK, intent);

                // CertificateActivity를 종료합니다.
                finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력한 텍스트를 초기화합니다.
                certificateText.setText("");
            }
        });
    }
}
