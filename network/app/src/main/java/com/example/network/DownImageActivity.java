package com.example.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownImageActivity extends AppCompatActivity {
    ImageView img1;
    Button btnLoad;

//    String imgUrl ="https://dy1yydbfzm05w.cloudfront.net/media/catalog/product/cache/466bc07fdd9f3406dd5cbfa6621097e2/d/u/dutch_tulip_persimmon_dutch_tulip_stem_2.jpg";
    String imgUrl = Common.SERVER_URL + "/mobile/images/rose.jpg";  // 서버에 업로드한 이미지
    Bitmap bm;
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            img1.setImageBitmap(bm);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_image);
        btnLoad = findViewById(R.id.btnLoad);
        img1 = findViewById(R.id.img1);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downImg(imgUrl);
            }
        });
    }

    public void downImg(final String file) {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(file);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bm = BitmapFactory.decodeStream(is);
                    //handler.sendEmptyMessage(0);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img1.setImageBitmap(bm);
                        }
                    });
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }
}