package com.example.anyang_setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main);

        // personal_write_button에 대한 클릭 리스너를 설정합니다.
        findViewById(R.id.personal_write_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PersonalWriteActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(PersonalMainActivity.this, PersonalWriteActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.personal_gpt_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PersonalWriteActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(PersonalMainActivity.this, PersonalGptActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.personal_loocker_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PersonalWriteActivity로 이동하는 코드를 추가합니다.
                Intent intent = new Intent(PersonalMainActivity.this, PersonalLockerActivity.class);
                startActivity(intent);
            }
        });
    }
}
