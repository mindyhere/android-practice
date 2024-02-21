package com.example.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadActivity extends AppCompatActivity {
    Button btn;
    EditText edit_entry;
    FileInputStream fis;
    URL url;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    String result;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            edit_entry.setText(result);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        edit_entry = findViewById(R.id.edit_entry);
        makeFile();
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String file = "/data/data/" + getPackageName() + "/files/test.txt";
                    //String file = "/data/data/" + getPackageName() + "/files/rose.jpg";
                    fileUpload(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void makeFile() {
        String path = "/data/data/" + getPackageName() + "/files";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File("/data/data/" + getPackageName() + "/files/test.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            String str = "hello android :)";
            fos.write(str.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fileUpload(final String file) {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                httpFileUpload(Common.SERVER_URL + "/mobile/upload.do", file);
            }
        });
        th.start();
    }

    void httpFileUpload(String urlString, String file) {
        try {
            fis = new FileInputStream(file);
            url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            String[] arr = file.split("/");
            String file_name = arr[arr.length - 1];
            dos.writeBytes("Content-Disposition: form-data;name=\"file\";filename=\"" + file_name + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            int bytesAvailable = fis.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];
            int bytesRead = fis.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fis.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fis.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            fis.close();
            int ch;
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            result = b.toString().trim();
            dos.close();
            handler.sendEmptyMessage(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}