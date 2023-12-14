package com.example.graphic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
            backImg = getResources().getDrawable(R.drawable.back0);
            gunship = getResources().getDrawable(R.drawable.gunship);
            missile = getResources().getDrawable(R.drawable.missile);
            enemy = getResources().getDrawable(R.drawable.enemy);
            explosure = getResources().getDrawable(R.drawable.hit);

            fire = MediaPlayer.create(GameActivity.this, R.raw.fire);
            hit = MediaPlayer.create(GameActivity.this, R.raw.hit);
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
                    Missile m = mlist.get(i);
                    m.setMy(m.getMy() - 5);
                    if (m.getMx() < 0) {
                        mlist.remove(i);
                    }
                    //충돌여부 판정 → 두 이미지가 겹칠 때
                    //적(이미지)의 사각영역
                    Rect rect1 = new Rect(ex, ey, ex + enemyWidth, ey + enemyHeight);
                    //총알(이미지)의 사각영역
                    Rect rect2 = new Rect(m.getMx(), m.getMy(), m.getMx() + missileWidth, m.getMy() + missileHeight);
                    if (rect1.intersect(rect2)) {   //겹친 부분이 있으면
                        hit.start();
                        isHit = true;   //폭발상태로 변경
                        point += 100;
                        //폭발한 좌표를 저장
                        hx = ex;
                        hy = ey;
                        mlist.remove(i);    //총알 리스트에서 제거
                        ex = width - enemyWidth;    //적의 좌표 초기화
                    }
                }
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            width = getWidth();
            height = getHeight();
            gunshipWidth = gunship.getIntrinsicWidth();
            gunshipHeight = gunship.getIntrinsicHeight();
            missileWidth = missile.getIntrinsicWidth();
            missileHeight = missile.getIntrinsicHeight();
            enemyWidth = enemy.getIntrinsicWidth();
            enemyHeight = enemy.getIntrinsicHeight();
            hitWidth = explosure.getIntrinsicWidth();
            hitHeight = explosure.getIntrinsicHeight();

            x = width / 2 - gunshipWidth / 2;
            y = height - 50;

            mx = x + 20;
            my = y;
            ex = width - enemyWidth;
            ey = 50;
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            backImg.setBounds(0, 0, width, height);
            backImg.draw(canvas);
            gunship.setBounds(x, y, x + gunshipWidth, y + gunshipHeight);
            gunship.draw(canvas);
            if (isHit) {    //폭발 상태 → 폭발 이미지 출력
                explosure.setBounds(hx - 20, hy - 20, hx + hitWidth - 20, hy + hitHeight - 20);
                explosure.draw(canvas);
                try {
                    Thread.sleep(300);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isHit = false;  //폭발하지 않은 상태로 전환
            } else {    //폭발하지 않은 상태
                enemy.setBounds(ex, ey, ex + enemyWidth, ey + enemyHeight);
                enemy.draw(canvas);
            }

            //총알 출력
            for (int i = 0; i < mlist.size(); i++) {
                Missile m = mlist.get(i);
                missile.setBounds(m.getMx(), m.getMy(), m.getMx() + missileWidth, m.getMy() + missileHeight);
                missile.draw(canvas);
            }
            String str = "Point: " + point;
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(25);
            canvas.drawText(str, 0, 30, paint);
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
            postInvalidate();
            return super.onKeyDown(keyCode, event);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            isFire = true;
            fire.start();
            Missile ms = new Missile(x + gunshipWidth / 2, y);
            mlist.add(ms);
            return super.onTouchEvent(event);
        }
    }
}