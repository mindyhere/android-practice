package com.example.ex01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BmiResult extends AppCompatActivity {
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_result);

        txtResult = (TextView) findViewById(R.id.txtResult);
//                              아이디(txtResult)의 주소를 연결

        Intent intent = getIntent(); //이전 화면(Bmi)에서 넘어온 정보
        BmiDTO dto = (BmiDTO) intent.getSerializableExtra("dto");
        String result = dto.getResult();
        String name = dto.getName();
        int age = dto.getAge();
        double height = dto.getHeight();
        double weight = dto.getWeight();
        double bmi = dto.getBmi();

        txtResult.setText("이름: " + name + "\n나이: " + age + "\n신장: " + height + "\n체중: " + weight + "\nbmi: " + bmi + "\n" + result);
    }
}