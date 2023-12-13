package com.example.graphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ShapeActivity extends AppCompatActivity {
    int x = 50, y = 50; //원의 중심좌표
    int width, height;  //화면의 가로, 세로 길이
    int moveX = 5, moveY = 10;
    int red, green, blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));   //커스텀 뷰. 사용자 정의
    }

    class MyView extends View implements Runnable {
        public MyView(Context context) {
            super(context);
            Thread th = new Thread(this);
            th.start();
        }

        //뷰 사이즈가 변경될 때 호출
        //최초 뷰가 출력될 때. 폰의 가로세로 방향이 바뀔 때
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            //디바이스의 가로, 세로 사이즈 계산
            width = getWidth();
            height = getHeight();
        }

        @Override   //백그라운드 작업(좌표값을 변경, 그래픽 개신 요청)
        public void run() {
            while (true) {
                //좌우 벽 처리
                if (x > (width - 20) || x < 20) {
                    moveX = -moveX;
                    setColor();
                }

                //상하 벽처리
                if (y > (height - 20) || y < 20) {
                    moveY = -moveY;
                    setColor();
                }

                //x, y 좌표 변경
                x += moveX;
                y += moveY;

                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //그래픽 갱신 요청 → onDraw() 호출됨
                postInvalidate();
            }
        }

        void setColor() {
            Random rand = new Random();
            red = rand.nextInt(256);    //0~255
            green = rand.nextInt(256);
            blue = rand.nextInt(256);
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.LTGRAY); //바탕화면 색상
            Paint p = new Paint();
            p.setColor(Color.argb(255, red, green, blue));  //색상 설정

            //캔버스에 원 그리기(x, y, 반지름, paint)
            canvas.drawCircle(x, y, 20, p);
        }
    }
}