package com.example.ex01;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Yut extends AppCompatActivity {
    //필드변수 → 각 자료형의 기본값으로 자동초기화
    int[] imgYut = {R.drawable.yut_0, R.drawable.yut_1};
    //              R: res 리소스 → R.drawable.~: res/drawable 이미지 디렉토리 참조(주소값)
    String[] strYut = {"윷", "걸", "개", "도", "모"};
    //                      0     1    2     3     4
    Button button1; //null로 자동초기화

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//  화면 생성될 때 호출
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yut);
//         화면레이아웃 세팅 → activity_yut.xml
        button1 = findViewById((R.id.button1));
//  참조변수 대입 ← button1의 주소값으로 초기화

        button1.setOnClickListener(new View.OnClickListener() {
            //          위젯.setOn이벤트종류Listener( 동작 → OnClickListener() 인터페이스 구현: 내부클래스 )
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int n1 = rand.nextInt(2); //0~1 정수발생(랜덤값)
                int n2 = rand.nextInt(2);
                int n3 = rand.nextInt(2);
                int n4 = rand.nextInt(2);
                int sum = n1 + n2 + n3 + n4;

                ImageView img1 = findViewById(R.id.img1);
                ImageView img2 = findViewById(R.id.img2);
                ImageView img3 = findViewById(R.id.img3);
                ImageView img4 = findViewById(R.id.img4);

//              이미지 이미지 리소스 지정   주소값
                img1.setImageResource(imgYut[n1]);
                img2.setImageResource(imgYut[n2]);
                img3.setImageResource(imgYut[n3]);
                img4.setImageResource(imgYut[n4]);

                TextView txtResult = findViewById(R.id.txtResult);
                txtResult.setText(strYut[sum]);
//                 텍스트뷰.setText(값)
            }
        });
    }
}