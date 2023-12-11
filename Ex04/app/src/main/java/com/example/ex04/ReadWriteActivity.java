package com.example.ex04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadWriteActivity extends AppCompatActivity {
    Button btnSave, btnLoad, btnDelete;
    EditText edit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_write);

        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);
        btnDelete = findViewById(R.id.btnDelete);
        edit1 = findViewById(R.id.edit1);

        btnSave.setOnClickListener(v -> {
            //Anonymous new View.OnClickListener() can be replaced with lambda
            //람다식: 전달할값 -> {실제 수행기능}
            File dir = new File("/data/data/" + getPackageName() + "/files");
            //디바이스의 내부디렉토리(앱 내부 저장소) → /data/data/패키지이름/files
            if (!dir.exists()) dir.mkdir();     //디렉토리가 없으면 생성
            File file = new File("/data/data/" + getPackageName() + "/files/test.txt");
            try {
                //프로그램 →(outputstream, 1바이트)→ 파일
                //(예) "hello" → {'h', 'e', 'l',' l', 'o'}
                FileOutputStream fos = new FileOutputStream(file);
                String str = edit1.getText().toString();
                fos.write(str.getBytes());  //스트링 → 바이트배열로
                fos.close();
                Toast.makeText(ReadWriteActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnLoad.setOnClickListener(v -> {
            try {
                //프로그램 →(inputstream)→ 파일
                InputStream is = openFileInput("test.txt");
                // 파일열기 메서드(openFileInput) data/data/패키지/files 디렉토리
                if (is != null) {
                    InputStreamReader reader = new InputStreamReader(is, "utf-8");
                    BufferedReader br = new BufferedReader(reader);
                    String str = "";
                    StringBuffer sb = new StringBuffer();

                    while ((str = br.readLine()) != null) {
                        //한 라인을 읽음 → null이 아닐 때,
                        sb.append(str + "\n");  //스트링버퍼에 추가
                    }
                    is.close();
                    edit1.setText(sb.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnDelete.setOnClickListener(v -> {
            deleteFile("test.txt");
            //파일삭제 메서드(파일명)
            Toast.makeText(ReadWriteActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
        });
    }
}