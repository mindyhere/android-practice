package com.example.ex04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class RestoreActivity extends AppCompatActivity {
//    외부저장소에 백업된 test.txt 파일을 내부저장소에 복원
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getExternalFilesDir(null).getAbsolutePath();
        Log.i("test", "path: " + path);

        try {
            File src = new File(path + "/test.txt");
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(src));
            FileOutputStream out = new FileOutputStream("/data/data/" + getPackageName() + "/files/test.txt");
            int data = 0;
            while ((data = in.read()) != -1) {
                out.write(data);
            }
            in.close();
            out.close();
            Toast.makeText(this, "복원이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}