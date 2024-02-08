package com.example.mobileweb;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class BrowserDemo1 extends AppCompatActivity {
    WebView webView1; // 웹브라우저 객체 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_demo1);

        webView1 = (WebView) findViewById(R.id.webview1);

        webView1.setWebViewClient(new WebViewClient());
        // 내장 브라우저가 구동되지 않고 현재 웹뷰에 브라우징이 되도록 처리
        webView1.getSettings().setJavaScriptEnabled(true);
        // setJavaScriptEnabled true → 웹뷰 안에서 javascript 설정 활성화
        webView1.loadUrl("https://m.naver.com");
        //        loadUrl → 웹뷰 url 지정
    }
}