package com.example.ex01;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

public class CheckboxDemo extends AppCompatActivity {
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox_demo);
        check = (CheckBox) findViewById(R.id.check);
//            ← 주소 연결                아이디(xml 내 변수명)

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//      체크박스.setOn이벤트Listener
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                                       체크박스 변수               true/false  체크여부
                if (isChecked) {
                    check.setText("체크된 상태");
                } else {
                    check.setText("체크되지 않은 상태");
                }
            }
        });
    }
}