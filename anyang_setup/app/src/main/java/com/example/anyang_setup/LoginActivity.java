package com.example.anyang_setup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mUsernameEditText = findViewById(R.id.username_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        mLoginButton = findViewById(R.id.login_button);

        Callback updateUserInfoCallback = new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("TEST", "responseData : " + jsonObject);
                    progressDialog.dismiss();

                    if(jsonObject.getBoolean("success")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userinfo", jsonObject.toString());
                        startActivity(intent);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "ID 또는 PW를 확인하세요", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("TEST", "ERROR Message : " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "서버 응답시간 초과", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        };

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(LoginActivity.this, "Loading", "로그인 중...\n최장 1분정도 소요됩니다.");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", mUsernameEditText.getText().toString()); // 2019U1132
                    jsonObject.put("pw", mPasswordEditText.getText().toString()); // !@#atlantis771
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.MINUTES)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString()
                        );

                Request request = new Request.Builder()
                        .url("http://138.2.126.137:3007/login")
                        .post(requestBody)
                        .build();

                client.newCall(request).enqueue(updateUserInfoCallback);
            }
        });

    }
}