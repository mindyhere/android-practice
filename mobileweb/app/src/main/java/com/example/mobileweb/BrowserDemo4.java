package com.example.mobileweb;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BrowserDemo4 extends AppCompatActivity {
    WebView webView1;
    TextView txtReceive;
    EditText editInput;
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_demo4);

        webView1 = (WebView) findViewById(R.id.webview1);
        txtReceive = (TextView) findViewById(R.id.txtReceive);
        editInput = (EditText) findViewById(R.id.editInput);
        btnSend = (Button) findViewById(R.id.btnSend);

        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.loadUrl("file:///android_asset/html/test.html");
        // 웹뷰           로컬html
        webView1.addJavascriptInterface(new AndroidBridge(), "android");
        //       addJavascriptInterface(    브릿지클래스,             "별칭")
        // → 웹뷰와 통신할 수 있는 인터페이스 클래스 설정

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 자바스크립트 함수 호출
                webView1.loadUrl("javascript:setMessage('" + editInput.getText() + "')");
            }
        });
    }

    // 핸들러 선언
    // (Alt+Enter, import class, android.os 선택)
    Handler handler = new Handler();

    // 앱-웹을 연결하는 브릿지 클래스
    class AndroidBridge {
        @JavascriptInterface    // 자바스크립트에서 호출할 수 있는 method
        public void setMessage(final String arg) { // 반드시 final로 선언
            //핸들러에게 메시지 전달
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // 핸들러가 메시지를 받아서 메인화면을 변경시킴
                    txtReceive.setText("받은 메세지 : " + arg);
                }
            });
        }
    }
}