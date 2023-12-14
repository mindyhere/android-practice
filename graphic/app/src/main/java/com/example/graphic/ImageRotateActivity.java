package com.example.graphic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ImageRotateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    class MyView extends View {

        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            Log.i("test", "width: " + getWidth() + ", height: " + getHeight());
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            //화면의 가운데 좌표
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int w = 0;
            int h = 0;
            int Direction = 2;
            Bitmap[] rose = new Bitmap[4];  //비트맵 배열
            Resources res = getResources(); //리소스 디렉토리

            // drawable 디렉토리의 리소스를 비트맵으로 변환
            rose[0] = BitmapFactory.decodeResource(res, R.drawable.rose_1);
            rose[1] = BitmapFactory.decodeResource(res, R.drawable.rose_2);
            rose[2] = BitmapFactory.decodeResource(res, R.drawable.rose_3);
            rose[3] = BitmapFactory.decodeResource(res, R.drawable.rose_4);

            Paint paint = new Paint();
            paint.setColor(Color.RED);

            //색상채우기 + 테두리선
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawColor(Color.WHITE);  //배경색
            switch (Direction) {
                case 1:
                    w = rose[0].getWidth() / 2;
                    h = rose[0].getHeight();
                    break;
                case 2:
                    w = 0;
                    h = rose[1].getHeight();
                    break;
                case 3:
                    w = rose[2].getWidth();
                    h = 0;
                    break;
                case 4:
                    w = rose[3].getWidth();
                    h = rose[3].getHeight() / 2;
                    break;
            }
            //이미지 출력(이미지,x,y,페인트객체)
            canvas.drawBitmap(rose[Direction - 1], 10, 10, null);
            canvas.drawCircle(w + 10, h + 10, 10, paint);

            for (int i = 1; i <= 18; i++) {
                //이미지 회전( 회전각도, 기준x좌표, 기준y좌표 )
                canvas.rotate(20, centerX, centerY);
                canvas.drawBitmap(rose[Direction - 1], centerX - w, centerY - h, null);
            }
        }
    }
}