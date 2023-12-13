package com.example.graphic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView view = new MyView(this);
        view.setFocusable(true);
        setContentView(view);
    }

    class MyView extends View implements Runnable {
        Drawable backImg;
        Drawable gunship;
        Drawable missile;
        Drawable enemy;
        Drawable explosure;
        MediaPlayer fire, hit;
        int width, height;
        int gunshipWidth, gunshipHeight;
        int missileWidth, missileHeight;
        int enemyWidth, enemyHeight;
        int hitWidth, hitHeight;
        int x, y;
        int mx, my;
        int ex, ey;
        int hx, hy;
        int point = 1000;
        boolean isFire;
        boolean isHit;
        List<Missile> mlist;

        public MyView(Context context) {
            super(context);
            //이미지 생성
            backImg = getResources().getDrawable(R.drawable.back0);
            gunship = getResources().getDrawable(R.drawable.gunship);
            missile = getResources().getDrawable(R.drawable.missile);
            enemy = getResources().getDrawable(R.drawable.enemy);
            explosure = getResources().getDrawable(R.drawable.hit);

            //사운드 생성
            fire = MediaPlayer.create(GameActivity.this, R.raw.fire);
            hit = MediaPlayer.create(GameActivity.this, R.raw.hit);

            //리스트 생성
            mlist = new ArrayList<>();
            Thread th = new Thread(this);
            th.start();
        }

        @Override
        public void run() {
            while (true) {
                ex -= 3;
                if (ex < 0) {
                    ex = width - enemyWidth;
                }
                for (int i = 0; i < mlist.size(); i++) {
                    Missile m = mlist.get(i);   //i번째 총알
                    m.setMy(m.getMy() - 5);     //y좌표 감소 처리

                    if (m.getMx() < 0) {    //y좌표가 0이면
                        mlist.remove(i);    //리스트에서 제거
                    }
                    //충돌여부 판정
                    //적의 사각영역
                    Rect rect1 = new Rect(ex, ey, ex + enemyWidth, ey + enemyHeight);
                    Rect rect2 = new Rect(m.getMx(), m.getMy(), m.getMx() + missileWidth, m.getMy() + missileHeight);

                    if (rect1.intersect(rect2)) {
                        hit.start();    //폭발음 플레이
                        isHit = true;   //폭발 상태로 변경
                        point += 100;   //점수증가
                        //폭발한 좌표 저장
                        hx = ex;
                        hy = ey;
                        mlist.remove(i);    //총알 리스트에서 제거
                        ex = width - enemyWidth;    //적 좌표 초기화
                    }
                }
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                postInvalidate();   //화면갱신 → onDraw()호출
            }
        }

        @Override   //화면 사이즈가 변경될 때(최초 표시, 가로/세로 전환)
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            //화면의 가로세로
            width = getWidth();
            height = getHeight();

            //이미지의 가로,세로 길이
            gunshipWidth = gunship.getIntrinsicWidth();
            gunshipHeight = gunship.getIntrinsicHeight();
            missileWidth = missile.getIntrinsicWidth();
            missileHeight = missile.getIntrinsicHeight();
            enemyWidth = enemy.getIntrinsicWidth();
            enemyHeight = enemy.getIntrinsicHeight();
            hitWidth = explosure.getIntrinsicWidth();
            hitHeight = explosure.getIntrinsicHeight();

            //비행기좌표
            x = width / 2 - gunshipWidth / 2;
            y = height - 50;

            //총알좌표
            mx = x + 20;
            my = y;

            //적 좌표
            ex = width - enemyWidth;
            ey = 50;
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            //배경이미지 출력 → setBounds(x1,y1,x2,y2) 영역 지정
            backImg.setBounds(0, 0, width, height);
            backImg.draw(canvas);   //이미지를 캔버스에 출력시킨다

            //비행기 출력
            gunship.setBounds(x, y, x + gunshipWidth, y + gunshipHeight);
            gunship.draw(canvas);

            //적 출력
            if (isHit) {
                //폭발 상태 → 폭발 이미지 출력
                explosure.setBounds(hx - 20, hy - 20, hx + hitWidth - 20, hy + hitHeight - 20);
                explosure.draw(canvas);
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                isHit = false;  //폭발하지 않은 상태로 전환
            } else {
                //폭발하지 않은 상태
                enemy.setBounds(ex, ey, ex + enemyWidth, ey + enemyHeight);
                enemy.draw(canvas);
            }

            //총알 출력
            for (int i = 0; i < mlist.size(); i++) {
                Missile m = mlist.get(i);   //i번째 총알
                //총알 이미지의 출력범위
                missile.setBounds(m.getMx(), m.getMy(), m.getMy() + missileWidth, m.getMy() + missileHeight);
                missile.draw(canvas);   //총알 이미지 출력
            }

            // 점수출력
            String str = "Point: " + point;
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(25);
            canvas.drawText(str, 0, 30, paint);
            //텍스트출력 → drawText(문자열, x, y, 페인트객체)
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    x -= 5;
                    x = Math.max(0, x);
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    x += 5;
                    x = Math.min(width - gunshipWidth, x);
                    break;
            }
            postInvalidate();   //그래픽 갱신요청 → onDraw() 호출
            return super.onKeyDown(keyCode, event);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            isFire = true;  //발사 상태로 전환
            fire.start();   //사운드 플레이

            //총알 객체생성
            Missile ms = new Missile(x + gunshipWidth / 2, y);
            mlist.add(ms);  //리스트에 추가
            return super.onTouchEvent(event);
        }
    }
}