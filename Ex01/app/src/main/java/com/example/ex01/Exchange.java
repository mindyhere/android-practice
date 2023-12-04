package com.example.ex01;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Exchange extends AppCompatActivity {
    //  필드변수
    EditText dollar;
    Button button;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        dollar = findViewById(R.id.dollar);
        button = findViewById(R.id.button);
        txtResult = findViewById(R.id.txtResult);
//      변수에 저장 ← 뷰 id로 찾기 (위젯/뷰 의 id : 주소값)

        button.setOnClickListener(new View.OnClickListener() {
//         객체.setOn이벤트Listener(동작)
            @Override
            public void onClick(View v) {
                if (dollar.getText().toString().equals("")) {
//                    Editable getText() 스트링으로
                    Toast.makeText(Exchange.this, "숫자를 입력하세요. ", Toast.LENGTH_LONG).show();
//                   Toast.makeText(화면, 메세지, 옵션).show() → 나타났다 사라지는 알림메세지
                    return;
                }
                int intDollar = Integer.parseInt(dollar.getText().toString());
                int money = intDollar * 1100;
                txtResult.setText(intDollar + "달러 = " + money + " 원 입니다.");
            }
        });
    }
}