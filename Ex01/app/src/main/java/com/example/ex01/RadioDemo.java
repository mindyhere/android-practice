package com.example.ex01;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class RadioDemo extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    RadioButton rdoRed, rdoGreen, rdoBlue;
    RadioGroup radio1;
    View layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_demo);

//      참조변수(위젯: 화면구성 요소들)에 xml 리소스 주소 연결
        rdoRed = findViewById(R.id.rdoRed);
        rdoGreen = findViewById(R.id.rdoGreen);
        rdoBlue = findViewById(R.id.rdoBlue);
        radio1 = findViewById(R.id.radio1);
        layout1 = findViewById(R.id.layout1);

        radio1.setOnCheckedChangeListener(this);
//      라디오그룹    체크상태가 바뀌면       (동작→현재 클래스 내부에서 처리)
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                                라디오그룹            각 라디오버튼의 아이디
        Log.i("test", "checkedId: " + checkedId);
        switch (checkedId) {
            case R.id.rdoRed:
                layout1.setBackgroundColor(Color.RED);  //배경색상 변경
                break;
            case R.id.rdoGreen:
                layout1.setBackgroundColor(Color.GREEN);
                break;
            case R.id.rdoBlue:
                layout1.setBackgroundColor(Color.BLUE);
                break;
        }
    }
}