package com.example.ex01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//                  화면생성 데이터
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        화면세팅      화면레이아웃 activity_main.xml 화면구성파일
        Log.i("test", "onCreate");
//        Log.종류(태그, 메세지)
    }

    @Override
    protected void onResume() { //화면재시작
        super.onResume();
        Log.i("test", "onResume");
    }

    @Override
    protected void onPause() { //화면멈춤
        super.onPause();
        Log.i("test", "onPause");
    }

    @Override
    protected void onDestroy() { //화면종료
        super.onDestroy();
        Log.i("test", "onDestroy");
    }

    public void mOnClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
//         메인화면에서 클릭한 버튼아이디
            case R.id.button1:
                intent = new Intent(this, ImageViewDemo.class);
                break;

            case R.id.button2:
                intent = new Intent(this, Exchange.class);
                break;
            case R.id.button3:
                intent = new Intent(this, Bmi.class);
                break;
            case R.id.button4:
                intent = new Intent(this, CheckboxDemo.class);
                break;
            case R.id.button5:
                intent = new Intent(this, RadioDemo.class);
                break;
            case R.id.button6:
                intent = new Intent(this, Yut.class);
                break;
            case R.id.button7:
                intent = new Intent(this, Scroll.class);
                break;
        }
        startActivity(intent); //화면전환
    }
}