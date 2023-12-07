package com.example.ex02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SpinnerActivity extends AppCompatActivity {
    TextView txtResult;
    Spinner spinner1;
    String[] arr = {"포도", "사과", "배", "귤", "딸기", "감", "대추"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        // 객체생성
        txtResult = findViewById(R.id.txtResult);
        spinner1 = findViewById(R.id.spinner1);

        // 어댑터 생성
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        // new ArrayAdapter<자료형>(컨텍스트, 레이아웃, 데이터) →             화면              사용할 child view(내장)
        adapter.setDropDownViewResource(R.layout.spinner_row);
        // 커스텀 child view: 스피너에 어댑터를 연결 → 데이터가 뷰로 변환
        spinner1.setAdapter(adapter);

        //스피너의 아이템선택 이벤트처리
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override   //아이템 선택 시
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtResult.setText(arr[position]);
            }

            @Override   //아이템을 선택하지 않을 때
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}