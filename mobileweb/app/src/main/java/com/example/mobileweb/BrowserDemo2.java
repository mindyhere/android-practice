package com.example.mobileweb;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class BrowserDemo2 extends AppCompatActivity {
    WebView webView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_demo1);

        webView1 = (WebView) findViewById(R.id.webview1);
        String html = "<html><body>Hello world :)</body></html>";
        // (1) html 문자열 → 웹뷰에 로드
        //webView1.loadData(html, "text/html; charset=utf-8", null);

        // (2) assets 폴더에 미리 저장된 html 파일을 불러오기 → assets/html/hello.html
        webView1.loadUrl("file:///android_asset/html/hello.html");
    }
}