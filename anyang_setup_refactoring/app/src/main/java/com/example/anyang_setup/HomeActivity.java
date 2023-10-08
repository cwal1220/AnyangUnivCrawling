package com.example.anyang_setup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.anyang_setup.Chatting.ChatActivity;
import com.example.anyang_setup.Info.UserInfoActivity;
import com.example.anyang_setup.Setting.SettingActivity;
import com.example.anyang_setup.Spec.SpecActivity;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity{

    private NavigationBarView navigationBarView;
    private String userinfo;
    private JSONObject userInfoJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationBarView = findViewById(R.id.bottom_navigationview);
        userinfo = getIntent().getStringExtra("userinfo");

        try {
            userInfoJson = new JSONObject(userinfo);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.home :
                    {
                        Intent intent = new Intent(HomeActivity.this, UserInfoActivity.class);
                        intent.putExtra("userinfo", userinfo);
                        startActivity(intent);
                        break;
                    }
                    case R.id.setting :
                    {
                        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                        intent.putExtra("userinfo", userinfo);
                        startActivity(intent);
                        break;
                    }
                    case R.id.info :
                    {
                        try {
                            JSONObject data = userInfoJson.getJSONObject("data");
                            String Name = data.getString("stdName");
                            Log.e("Telechips", Name);

                            Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                            intent.putExtra("name", Name);
                            startActivity(intent);
                        }
                        catch (JSONException e)
                        {
                            throw new RuntimeException(e);
                        }

                        break;
                    }
                    case R.id.spec :
                    {
                        Intent intent = new Intent(HomeActivity.this, SpecActivity.class);
                        intent.putExtra("userinfo", userinfo);
                        startActivity(intent);
                        break;
                    }
                }

                return true;
            }
        });
    }

}