package com.example.network;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    TextView txtResult;
    EditText editId, editPwd;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtResult = (TextView) findViewById(R.id.txtResult);
        editId = (EditText) findViewById(R.id.editId);
        editPwd = (EditText) findViewById(R.id.editPwd);
    }

    public void login(View v) {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String page = Common.SERVER_URL + "/mobile/webview_servlet/login_check.do?";
                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    String param = "userid=" + editId.getText().toString() + "&passwd=" + editPwd.getText().toString();

                    StringBuilder sb = new StringBuilder();

                    if (conn != null) {
                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("POST");
                        conn.setUseCaches(false);
                        conn.getOutputStream().write(param.getBytes("utf-8"));
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                            while (true) {
                                String line = br.readLine();
                                if (line == null) {
                                    break;
                                }
                                sb.append(line + "\n");
                            }
                            br.close();
                        }
                        conn.disconnect();
                        // → (ex) {"name":"DB 상 이름"} or {"name":"null"} 로 데이터가 넘어옴

                        JSONObject jsonObj = new JSONObject(sb.toString());
                        String name = jsonObj.getString("name");
                        if (name != null && !name.equals("null")) {
                            result = name + "님 환영합니다.";
                        } else {
                            result = "아이디 또는 비밀번호가 일치하지 않습니다.";
                        }

                        // 백그라운드 작업
                        runOnUiThread(new Runnable() {
                        //  ui 스레드(main thread) 현재화면 담당
                            @Override
                            public void run() {
                                txtResult.setText(result);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        th.start(); // 백그라운드 스레드 start → run()
    }
}