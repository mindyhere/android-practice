package com.example.graphic;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.util.Calendar;

public class ClockActivity extends AppCompatActivity {
    boolean isRun;
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));   //커스텀뷰. 사용자정의 뷰
    }

    @Override
    //뒤로가기 키를 눌렀을 때 호출 → 화면이 종료될 때 프로그램 실행을 멈춤. 생략할 경우 프로그램이 백그라운드에서 계속 돌아가게 된다.
    protected void onPause() {
        super.onPause();
        isRun = false;
        t = null;
    }

    //내부클래스
    class MyView extends View implements Runnable {
        int cx, cy, cw;
        int watchW, watchH;
        Bitmap clock;
        Bitmap pins[] = new Bitmap[3];
        int hour, min, sec;
        float rHour, rMin, rSec;
        int width, height;

        public MyView(Context context) {
            //생성자
            super(context);

            //화면 가로세로
            DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();
            width = disp.widthPixels;
            height = disp.heightPixels;

            //가운데 좌표
            cx = width / 2;
            cy = (height - 100) / 2;

            //비트맵 이미지
            Resources res = context.getResources();
            clock = BitmapFactory.decodeResource(res, R.drawable.dial);
            pins[0] = BitmapFactory.decodeResource(res, R.drawable.pin_1);
            pins[1] = BitmapFactory.decodeResource(res, R.drawable.pin_2);
            pins[2] = BitmapFactory.decodeResource(res, R.drawable.pin_3);

            //시계의 중심좌표
            cw = clock.getWidth() / 2;
            watchW = pins[0].getWidth() / 2;
            //          초침  가로사이즈
            watchH = pins[0].getHeight() - 10;

            //백그라운드 스레드 생성
            Thread th = new Thread(this);
            isRun = true;   //스레드 실행중
            th.start();
        }

        void calcTime() {
            //현재 시각 계간
            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);  //24시간제로 표시
            min = calendar.get(Calendar.MINUTE);
            sec = calendar.get(Calendar.SECOND);

            //시분초 → 각도 변환
            rSec = sec * 6; // 360도 / 60초 → 6도
            rMin = min * 6;
            rHour = hour * 30 + rMin / 12;
            //Log.i("test", "calcTime: " + hour + " " + min + " " + sec); → 콘솔창에서 확인 출력되는 시간 확인
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            calcTime(); //시간계산
            canvas.drawColor(Color.WHITE);  //배경색상 설정

            // scale(가로비율, 세로비율, x축,y축)
            canvas.scale(0.8f, 1, cx, cy);  //캔버스 사이즈 조절
            // rotate(각도, x,y )
            canvas.rotate(0, cx, cy);   //캔버스 회전
            canvas.drawBitmap(clock, cx - cw, cy - cw, null);

            //시침 출력
            canvas.rotate(rHour, cx, cy);
            canvas.drawBitmap(pins[2], cx - watchW, cy - watchH, null);
            //분침 출력
            canvas.rotate(rMin - rHour, cx, cy);
            canvas.drawBitmap(pins[1], cx - watchW, cy - watchH, null);
            //초침 출력
            canvas.rotate(rSec - rMin, cx, cy);
            canvas.drawBitmap(pins[0], cx - watchW, cy - watchH, null);
            canvas.rotate(-rSec, cx, cy);

            //시간 출력
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setTextSize(100);
            canvas.drawText(String.format("%02d: %02d: %02d", hour, min, sec), cx - 120, cy + cw + 150, paint);
        }

        @Override
        public void run() {
            while (isRun) {
                postInvalidate();   // 화면 갱신 요청 onDraw() 호출
                Log.i("test", "실행 중");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}