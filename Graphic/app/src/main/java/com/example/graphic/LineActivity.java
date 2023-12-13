package com.example.graphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LineActivity extends AppCompatActivity {
    List<Point> points = new ArrayList<>();
    //좌표값을 저장할 리스트

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
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.LTGRAY);
            Paint paint = new Paint(); //페인트 객체생성
            paint.setColor(Color.BLUE);  //페인트의 색성 설정
            paint.setStrokeWidth(10);   //선의 굵기
            Log.i("test","point 개수: "+points.size());
            for (int i = 0; i < points.size(); i++) {
                Point now = points.get(i);  //현재 좌표
                Log.i("test", now.x+","+ now.y);
                if (now.isDraw) {   //movet 상태 이면
                    Point before = points.get(i - 1);   //이전 좌표값
                    canvas.drawLine(before.x, before.y, now.x, now.y, paint);
                }
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //처음 터치할 때
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                points.add(new Point(event.getX(), event.getY(), false));
                return true;
            }
            //터치한 상태에서 손가락을 움직일 때
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                points.add(new Point(event.getX(), event.getY(), true));
                invalidate();   //화면 갱신요청
                return true;
            }
            return false;   //터치 이벤트 무시
        }
    }
}