package com.example.mobileweb;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class BrowserDemo3 extends AppCompatActivity {
    WebView webView1;
    EditText editUrl;
    Button btnOk, btnBack, btnNext, btnCancel;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_demo3);

        // 변수-리소스 연결
        webView1 = findViewById(R.id.webview1);
        editUrl = findViewById(R.id.editUrl);
        btnOk = findViewById(R.id.btnOk);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnCancel = findViewById(R.id.btnCancel);
        pb = findViewById(R.id.pb);

        WebSettings set = webView1.getSettings();
        set.setJavaScriptEnabled(true); // 웹뷰 안에서 자바스크립트 허용
        set.setBuiltInZoomControls(true);   // 페이지에서 줌 기능 허용
        webView1.loadUrl("http://google.com");  // 시작페이지 url
        webView1.setWebViewClient(new WebViewClient());     // 현재 앱 브라우저
        webView1.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                // 프로그래스 진행률
                // VISIBLE 표시, INVISIBLE 숨김, GONE 제거
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                    // 완료(100) 시 제거(GONE)
                } else {
                    pb.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editUrl.getText().toString();
                if (url.indexOf("http://") == -1) {
                    url = "http://" + url;
                }
                webView1.loadUrl(url);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUrl.setText("");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView1.canGoBack()) {
                    webView1.goBack();  // 직전 url로 이동
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView1.canGoForward()) {
                    webView1.goForward();   // 다음 url로 이동
                }
            }
        });
    }
}