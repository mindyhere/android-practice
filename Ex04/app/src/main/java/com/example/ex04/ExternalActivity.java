package com.example.ex04;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class ExternalActivity extends AppCompatActivity {
    Button btnSave, btnLoad, btnDelete;
    EditText edit1;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_write);

        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);
        btnDelete = findViewById(R.id.btnDelete);
        edit1 = findViewById(R.id.edit1);

        path = getExternalFilesDir(null).getAbsolutePath();
        //     외부 파일 디렉토리                절대경로
        //앱 외부저장소→ /sdcard/Android/data/패키지이름/files 디렉토리에 저장됨
        //Android SDK 32까지는 외부 저장소 접근 시 사용권한 필요, 33부터는 사용권한을 체크하지 않음 → build.gradle에서 환경설정 가능
        Log.i("test", "외부메모리 경로: " + path); //경로 확인을 위해 로그 라인추가
        //Log. → i info, w warning, e error, d debug

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    //실행하는 디바이스SDK 버전                     = SDK33
                    save();     //권한확인 필요x, 저장
                } else {
                    check();    //33이전 버전에서는 권한 체크
                }
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(path + "/test.txt");   //load된 파일명과 일치해야 한다.
                if (file.delete()) {
                    //true: 삭제성공 ↔ false: 삭제실패
                    edit1.setText("");
                    Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("test", "check here");     //삭제 이벤트 에러 → 로그 라인추가
                }
            }
        });
    }

    void load() {
        try {
            FileInputStream is = new FileInputStream(path + "/test.txt");

            if (is != null) {
                InputStreamReader reader = new InputStreamReader(is, "utf-8");
                BufferedReader br = new BufferedReader(reader);
                String str = "";
                StringBuilder sb = new StringBuilder();

                while ((str = br.readLine()) != null) {
                    sb.append(str + "\n");
                    //버퍼에서 한 라인을 읽어 스트링빌더에 추가&줄바꿈(\n) 포함
                }
                is.close();
                edit1.setText(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void check() {
        int check = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //          checkSelfPermission(화면, 권한) → 사용권한 체크. (권한부여: 0 ↔ 권한없으면: -1)
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            //             requestPermissions(화면, 사용권한리스트, 요청코드): Y/N → onRequestPermissionsResult
        } else {
            save();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    save();
                } else {
                    Toast.makeText(this, "외부메모리를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
        }
    }

    void save() {
        try {
            File dir = new File(path);
            //파일참조변수        디렉토리/파일
            Log.i("test", "dir: " + dir.exists());

            if (!dir.exists()) {
                try { //디렉토리가 없으면
                    dir.mkdir(); //디렉토리 생성
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            File file = new File(dir + "/test.txt");
            FileWriter writer = new FileWriter(file, false);
            //파일에 내용기록
            String str = edit1.getText().toString();
            writer.write(str);  //파일에 저장
            writer.close();
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}