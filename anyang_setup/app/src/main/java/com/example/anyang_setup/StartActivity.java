package com.example.anyang_setup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    private WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        myWebView = findViewById(R.id.webview1);

        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().equals("tis.anyang.ac.kr")) {
                    // 해당 도메인에 대해서는 WebView에서 처리
                    return false;
                } else {
                    // 다른 도메인에 대해서는 해당 도메인으로 WebView에서 페이지 이동
                    view.loadUrl(url);
                    return true;
                }
            }
        };

        myWebView.setWebViewClient(webViewClient);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.getSettings().setJavaScriptEnabled(true); // JavaScript 활성화

        // 쿠키 관리를 위한 설정
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(myWebView, true);
        CookieSyncManager.createInstance(this);

        myWebView.loadUrl("https://tis.anyang.ac.kr/main.do#");
        myWebView.setVisibility(View.GONE);

        // 2초 후에 다른 액티비티로 자동으로 넘어가게 하는 코드
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myWebView.evaluateJavascript(
                        "(function() { " +
                                "    var mainframeChildFrame = document.getElementById('mainframe_childframe'); " +
                                "    return mainframeChildFrame !== null; " +
                                "})();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String result) {
                                boolean isLoginSuccessful = Boolean.parseBoolean(result);
                                if (isLoginSuccessful) {
                                    // 로그인에 성공한 경우 메인 액티비티로 이동
                                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // 로그인에 실패한 경우 처리
                                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish(); // 이전 액티비티 종료
                                }
                            }
                        }
                );
            }
        }, 4000); // 5초 후에 실행
    }
}
