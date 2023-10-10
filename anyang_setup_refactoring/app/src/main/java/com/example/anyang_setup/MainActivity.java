package com.example.anyang_setup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private ProgressDialog progressDialog;
    private CheckBox autoLoginCheckBox;

    String loginId, loginPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        mUsernameEditText = findViewById(R.id.username_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        mLoginButton = findViewById(R.id.login_button);
        autoLoginCheckBox = findViewById(R.id.autoLoginCheckBox);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        loginId = auto.getString("inputId",null);
        loginPwd = auto.getString("inputPwd",null);

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
                                Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            }
                        });

                        if(autoLoginCheckBox.isChecked())
                        {
                            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor autoLogin = auto.edit();
                            autoLogin.putString("inputId", mUsernameEditText.getText().toString());
                            autoLogin.putString("inputPwd", mPasswordEditText.getText().toString());
                            autoLogin.commit();
                        }

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("userinfo", jsonObject.toString());
                        startActivity(intent);

                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "ID 또는 PW를 확인하세요", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "로그인 실패1", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainActivity.this, "스마트 캠퍼스에 연결할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        };

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(MainActivity.this, "Loading", "로그인 중...\n최장 2분 소요됩니다.");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", mUsernameEditText.getText().toString()); // 2019U1132
                    jsonObject.put("pw", mPasswordEditText.getText().toString()); // !@#atlantis771
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.MINUTES)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString()
                );

                Request request = new Request.Builder()
                        .url("http://158.179.161.134:3007/login")
                        .post(requestBody)
                        .build();

                client.newCall(request).enqueue(updateUserInfoCallback);
            }
        });

        if(loginId !=null && loginPwd != null)
        {
            mUsernameEditText.setText(loginId);
            mPasswordEditText.setText(loginPwd);
            autoLoginCheckBox.setChecked(true);

            progressDialog = ProgressDialog.show(MainActivity.this, "Loading", "로그인 중...\n최장 2분 소요됩니다.");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", mUsernameEditText.getText().toString()); // 2019U1132
                jsonObject.put("pw", mPasswordEditText.getText().toString()); // !@#atlantis771
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.MINUTES)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .build();

            RequestBody requestBody = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    jsonObject.toString()
            );

            Request request = new Request.Builder()
                    .url("http://158.179.161.134:3007/login")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(updateUserInfoCallback);
        }
    }
}