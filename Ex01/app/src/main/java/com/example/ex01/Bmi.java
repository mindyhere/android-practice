package com.example.ex01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Bmi extends AppCompatActivity {
    EditText editName, editAge, editHeight, editWeight;
    Button button1;
    TextView txtResult;
    //  위젯(화면구성 요소들) → 필드변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//                                   화면생성, 전달값
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
//      세팅 컨텐트뷰   (res/layout/activity_bmi.xml) → 화면레이아웃 파일지정

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editHeight = findViewById(R.id.editHeight);
        editWeight = findViewById(R.id.editWeight);
        button1 = findViewById(R.id.button1);
        txtResult = findViewById(R.id.txtResult);
//                  찾기  뷰 아이디로 (아이디)
//      Bmi.java 코드 ← activity_bmi.xml 화면구성(객제 주소연결)

        button1.setOnClickListener(new View.OnClickListener() {
//                  버튼.리스너(동작)
            @Override
            public void onClick(View v) {
                double kg = Double.parseDouble(editWeight.getText().toString());
                double height = Double.parseDouble(editHeight.getText().toString()) / 100;
                double bmi = kg / (height * height); //BMI계산 공식
                String result = "";

                if (bmi < 18.5) {
                    result = "저체중";
                } else if (bmi >= 18.5 && bmi < 23) {
                    result = "정상";
                } else if (bmi >= 23 && bmi < 25) {
                    result = "과체중";
                } else if (bmi >= 25 && bmi < 30) {
                    result = "비만";
                } else if (bmi >= 30) {
                    result = "고도비만";
                }

                result = editName.getText().toString() + " 님의 체중은 " + result + "입니다.";
//              txtResult.setText(result);
//              텍스트뷰.setText(값) → 현재 화면세팅

//              인텐트 Intent: 앱 컴포넌트가 무엇을 할 것인지 담는 통신 객체(메세지/데이터 전달)
                Intent intent = new Intent(Bmi.this, BmiResult.class);
//                                                   현재화면.this    다음화면.class
                BmiDTO dto = new BmiDTO();
                dto.setName(editName.getText().toString());
                dto.setAge(Integer.parseInt(editAge.getText().toString()));
                dto.setHeight(Double.parseDouble(editHeight.getText().toString()));
                dto.setWeight(Double.parseDouble(editWeight.getText().toString()));
                dto.setBmi(bmi);
                dto.setResult(result);
                intent.putExtra("dto", dto);
//                                     key , value 로 저장
                startActivity(intent); // 화면이동: MainActivity → (인텐트에 의해) Bmi → BmiResult
            }
        });
    }
}