package com.example.network;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownHtmlActivity extends AppCompatActivity {
    String html;

//    핸들러 활용
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            TextView result = (TextView) findViewById(R.id.result);
//            result.setText(html);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_html);
        Button btn = findViewById(R.id.down);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        html = downloadHtml(Common.SERVER_URL + "/mobile/main.jsp");
                        //handler.sendEmptyMessage(0);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView result = (TextView) findViewById(R.id.result);
                                result.setText(html);
                            }
                        });
                    }
                });
                th.start();
            }

            String downloadHtml(String addr) {
                StringBuffer html = new StringBuffer();
                try {
                    URL url = new URL(addr);    // String 을  URL 형식으로 변경
                    // 서버에 접속 시도
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    if (conn != null) {
                        conn.setConnectTimeout(10000);  // 접속시도 제한시간
                        conn.setUseCaches(false);   // 캐시 사용X
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            // 응답코드                 200(정상처리)
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                            for (; ; ) {
                                String line = br.readLine();
                                if (line == null) break;
                                html.append(line + "\n");
                            }
                            br.close();
                        }
                        conn.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return html.toString();
            }
        });
    }
}