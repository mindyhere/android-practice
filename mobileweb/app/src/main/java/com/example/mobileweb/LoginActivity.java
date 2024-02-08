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

public class LoginActivity extends AppCompatActivity {
    EditText editId, editPwd;
    Button btnLogin;
    TextView txtResult;
    WebView webView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editId = (EditText) findViewById(R.id.editId);
        editPwd = (EditText) findViewById(R.id.editPwd);
        txtResult = (TextView) findViewById(R.id.txtResult);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        webView1 = (WebView) findViewById(R.id.webview1);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.loadUrl("http://192.168.0.35/mobile/webview/login.jsp");
        //             웹서버ip → cmd, ipconfig로 주소 확인
        webView1.addJavascriptInterface(new AndroidBridge(), "android");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editId.getText().toString();
                String pwd = editPwd.getText().toString();
                webView1.loadUrl(
                        "javascript:login('" + id + "','" + pwd + "')");
                        // login.jsp의 login(userid, pwd){} 호출
            }
        });
    }

    Handler handler = new Handler();

    class AndroidBridge {
        @JavascriptInterface
        public void setMessage(final String arg) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    txtResult.setText(arg.trim());
                }
            });
        }
    }
}