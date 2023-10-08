package com.example.anyang_setup.Info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.anyang_setup.Info.SubActivity.DiagnosisActivity;
import com.example.anyang_setup.R;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoActivity extends AppCompatActivity {

    private String userinfo;
    private String mParam1;
    private String mParam2;
    private ScrollView scrollView;
    private Button button;
    private TextView getScoreText, remainScoreText, majorScoreText, generalScoreText;
    private String userInfoStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        final TextView textView = findViewById(R.id.Name);
        final TextView textView2 = findViewById(R.id.Major);

        userInfoStr = getIntent().getStringExtra("userinfo");
        scrollView = findViewById(R.id.scrollView_home);
        button = findViewById(R.id.diagnosis);
        getScoreText = findViewById(R.id.Earned_Credits);
        remainScoreText = findViewById(R.id.Remaining_Credits);
        majorScoreText = findViewById(R.id.major_Credits);
        generalScoreText = findViewById(R.id.Liberal_Arts_Credits);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, DiagnosisActivity.class);
                startActivity(intent);
            }
        });

        WebView webView = findViewById(R.id.webview_3);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webView.evaluateJavascript(
                                "document.getElementById('mainframe_childframe_form_topDiv_titleDiv_spaceDiv_AccessNameStaticTextBoxElement').innerText",
                                new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String value) {
                                        String result = value.replaceAll("^\"|\"$", "");
                                        textView.setText(result);
                                    }
                                });
                    }
                }, 3000); // 1초 대기

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webView.evaluateJavascript(
                                "document.getElementById('mainframe_childframe_form_leftContentDiv_widType_BTN_MENU_DIV_menuDiv_DG_LEFT_MENU_body_gridrow_5_cell_5_0_controltree').innerText",
                                new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String value) {
                                        String result = value.replaceAll("^\"|\"$", "");
                                        textView2.setText(result);
                                    }
                                });
                    }
                }, 3000); // 1초 대기

            }
        });

        webView.loadUrl("https://tis.anyang.ac.kr/main.do");

        try {
            JSONObject jsonObject = new JSONObject(userInfoStr);
            JSONObject creditStatus = jsonObject.getJSONObject("data").getJSONObject("creditStatus");
            Log.d("Telechips@@@@@@@", creditStatus.toString());
            getScoreText.setText(Integer.toString(creditStatus.getInt("total_current")));
            remainScoreText.setText(Integer.toString(creditStatus.getInt("total_remain")));
            majorScoreText.setText(Integer.toString(creditStatus.getInt("major_current")));
            generalScoreText.setText(Integer.toString(creditStatus.getInt("general_current")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}