package com.example.anyang_setup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ScrollView scrollView;
    private Button button;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        scrollView = view.findViewById(R.id.scrollView_home);
        button = view.findViewById(R.id.diagnosis);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DiagnosisActivity.class);
                startActivity(intent);
            }
        });

        final TextView textView = view.findViewById(R.id.Name);

        final TextView textView2 = view.findViewById(R.id.Major);

        WebView webView = view.findViewById(R.id.webview_3);
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

        return view;
    }
}