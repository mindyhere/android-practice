package com.example.graphic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class MoveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    class MyView extends View {
        int width, height;  //화면크기
        int cx, cy; //회전축
        int turnW, turnH;   //오뚜기의 회전축
        int sw, sh; //그림자의 크기
        int ang;    //현재 각도
        int dir;    //회전방향
        int limit1, limit2; //좌우의 한계점
        Bitmap imgBack, imgToy, imgShadow;

        public MyView(Context context) {
            //생성자
            super(context);
            DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();
            //화면의 가로세로
            width = disp.widthPixels;
            height = disp.heightPixels;

            //중심점
            cx = width / 2;
            cy = height / 2 + 100;

            imgBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.back);   //원본 배경이미지
            imgBack = Bitmap.createScaledBitmap(imgBack, width, height, true);  //뷰의 크기에 맞게 사이즈 조절
            imgToy = BitmapFactory.decodeResource(context.getResources(), R.drawable.toy);  //오뚜기 이미지
            imgShadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.shadow);    //그림자 이미지

            //오뚜기의 중심좌표
            turnW = imgToy.getWidth() / 2;
            turnH = imgToy.getHeight();
            //그림자의 중심좌표
            sw = imgShadow.getWidth() / 2;
            sh = imgShadow.getHeight() / 2;

            ang = 0;    //회전각도
            dir = 0;    //회전방향

            //sendEmptyMessageDelayed(메세지, 지연시간) → 일정시간(밀리세컨드) 후 메세지가 전달됨 : handleMessage method 호출
            handler.sendEmptyMessageDelayed(1, 10);
        }

        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {    //메세지 내용
                    invalidate();   //화면 갱신요청 → onDraw 호출
                    handler.sendEmptyMessageDelayed(1, 10);
                }
            }
        };

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            rotateToy();
            canvas.drawBitmap(imgBack, 0, 0, null);     //배경 이미지
            canvas.drawBitmap(imgShadow, cx - sw, cy - sh, null);    //그림자
            canvas.rotate(ang, cx, cy);     //캔버스를 회전시킨다
            canvas.drawBitmap(imgToy, cx - turnW, cy - turnH, null);    //오뚜기
            canvas.rotate(-ang, cx, cy);    //캔버스 회전 취소
        }

        void rotateToy() {  //오뚜기 회전 기능
            ang += dir; //회전각 증가, 감소

            if (ang <= limit1 || ang >= limit2) {   //좌우 한계점에 도달했을 때
                limit1++;   //왼쪽 증가
                limit2--;   //오른쪽 감소
                dir = -dir; //회전방향 변경
                ang += dir; //각도 변경
            }
            Log.i("test", "방향: " + dir + ", 각도: " + ang);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                limit1 = -30;   //왼쪽 한계
                limit2 = 30;    //오른쪽 한계

                if (dir == 0) {
                    dir = -1;   //왼쪽으로 기울임 처리
                }
            }
            return true;
        }
    }
}