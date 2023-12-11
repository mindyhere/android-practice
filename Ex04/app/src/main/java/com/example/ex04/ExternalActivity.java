package com.example.ex04;

import android.Manifest;
import android.content.pm.PackageManager;
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
        Log.i("text", "외부메모리 경로: " + path);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
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
                File file = new File(path + "/text.txt");
                if (file.delete()) {
                    edit1.setText("");
                    Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
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
        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
            Log.i("test", "dir: " + dir.exists());

            if (!dir.exists()) {
                try {
                    dir.mkdir();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            File file = new File(dir + "/test.txt");
            FileWriter writer = new FileWriter(file, false);
            String str = edit1.getText().toString();
            writer.write(str);
            writer.close();
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}