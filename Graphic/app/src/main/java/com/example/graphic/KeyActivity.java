package com.example.graphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class KeyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView view = new MyView(this);
        //키 이벤트를 받을 수 있도록 설정
        view.setFocusable(true);
        setContentView(view);
    }

    class MyView extends View {
        float x = 50, y = 50;   //원의 중심좌표
        int width, height;  //화면 사이즈

        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            width = getWidth();
            height = getHeight();
        }

        @Override   //뷰가 화면에 출력될 때 호출
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.LTGRAY); //배경색상
            Paint paint = new Paint(); //페인트 객체생성
            paint.setColor(Color.YELLOW); //페인트 색상
            //원 그리기: 캔버스.drawCircle(x, y, 반지름, 페인트객체)
            canvas.drawCircle(x, y, 50, paint);
        }

        @Override   //화면 터치 이벤트
        public boolean onTouchEvent(MotionEvent event) {
            //터치한 좌표의 x,y 값 저장
            x = event.getX();
            y = event.getY();
            //ㅘ면 갱신 요청 → onDraw()가 호출됨
            postInvalidate();
            return super.onTouchEvent(event);
        }

        @Override   //키 이벤트 처리
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            switch (keyCode) {  //입력된 키 코드 값
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    x -= 5;
                    x = Math.max(20, x);    //큰 값
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    x += 5;
                    x = Math.min(width - 20, x);    //작은 값
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    y -= 5;
                    y = Math.max(20, y);
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    y += 5;
                    y = Math.min(height - 20, y);
                    break;
            }
            postInvalidate();   //화면 갱신 요청
            return super.onKeyDown(keyCode, event);
        }
    }
}