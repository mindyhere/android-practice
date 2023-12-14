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

            turnW = imgToy.getWidth() / 2;
            turnH = imgToy.getHeight();

            sw = imgShadow.getWidth() / 2;
            sh = imgShadow.getHeight() / 2;

            ang = 0;
            dir = 0;

            handler.sendEmptyMessageDelayed(1, 10);
        }

        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    invalidate();
                    handler.sendEmptyMessageDelayed(1, 10);
                }
            }
        };

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            rotateToy();
            canvas.drawBitmap(imgBack, 0, 0, null);
            canvas.drawBitmap(imgShadow, cx - sw, cy - sh, null);
            canvas.rotate(ang, cx, cy);
            canvas.drawBitmap(imgToy, cx - turnW, cy - turnH, null);
            canvas.rotate(-ang, cx, cy);
        }

        void rotateToy() {
            ang += dir;
            if (ang <= limit1 || ang >= limit2) {
                limit1++;
                limit2--;
                dir = -dir;
                ang += dir;
            }
            Log.i("test", "방향: " + dir + ", 각도: " + ang);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                limit1 = -30;
                limit2 = 30;
                if (dir == 0) {
                    dir = -1;
                }
            }
            return true;
        }
    }
}