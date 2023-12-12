package com.example.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MenuCheckActivity extends AppCompatActivity {
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_check);
        txtResult = findViewById(R.id.txtResult);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();  //메뉴생성기
        inflater.inflate(R.menu.menu_check, menu);
        //              메뉴 생성           메뉴파일
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.bigfont) {
            if (item.isChecked()) {     //체크한 메뉴 확인 → 결과 반환
                txtResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            } else {
                txtResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            }
        } else if (item.getItemId() == R.id.red) {
            txtResult.setTextColor(Color.RED);
        } else if (item.getItemId() == R.id.green) {
            txtResult.setTextColor(Color.GREEN);
        } else if (item.getItemId() == R.id.blue) {
            txtResult.setTextColor(Color.BLUE);
        }
        return true;
    }

    @Override      //메뉴 생성 준비
    public boolean onPrepareOptionsMenu(Menu menu) {
        float px = txtResult.getTextSize();
        //         기존 텍스트 사이즈
        float sp = px / getResources().getDisplayMetrics().scaledDensity;
        //         절대값픽셀 / 해상도
        if (sp == 40) {
            menu.findItem(R.id.bigfont).setChecked(true);
        } else {
            menu.findItem(R.id.bigfont).setChecked(false);
        }
        int color = txtResult.getTextColors().getDefaultColor();
        if (color == Color.RED) {
            menu.findItem(R.id.red).setChecked(true);
        } else if (color == Color.GREEN) {
            menu.findItem(R.id.green).setChecked(true);
        } else if (color == Color.BLUE) {
            menu.findItem(R.id.blue).setChecked(true);
        }
        return true;
    }
}