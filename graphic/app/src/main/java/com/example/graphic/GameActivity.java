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
        MyView view = new MyView(this); //화면 → 내부클래스로 처리. 커스텀 뷰
        view.setFocusable(true);    // 키 이벤트를 받을 수 있도록 설정(키 입력 허용)
        setContentView(view);   //화면 설정
    }

    class MyView extends View implements Runnable {
        Drawable backImg;
        Drawable gunship;
        Drawable missile;
        Drawable enemy;
        Drawable explosure;

        MediaPlayer fire, hit;   // SoundPool 사운드(1M), MediaPlayer 사운드(1M 이상),동영상
        int width, height;  //화면의 가로세로 크기
        int gunshipWidth, gunshipHeight;    //비행기의 가로세로 크기
        int missileWidth, missileHeight;    //총알의 가로세로 크기
        int enemyWidth, enemyHeight;    //타겟의 가로세로 크기
        int hitWidth, hitHeight;    //폭발 이미지의 가로세로 크기
        int x, y;   //비행기의 좌표
        int mx, my; //총알의 좌표
        int ex, ey; //타겟의 좌표
        int hx, hy; //폭발(충돌) 시 좌표
        int point = 1000;   //기본점수
        boolean isFire; //총알 발사여부
        boolean isHit;  //충돌 여부
        List<Missile> mlist;    //발사된 총알 리스트

        public MyView(Context context) {
            //생성자
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

            //백그라운드 스레드 생성
            Thread th = new Thread(this);
            th.start();
        }

        @Override
        public void run() {
            while (true) {
                ex -= 3;
                if (ex < 0) { //왼쪽 벽
                    ex = width - enemyWidth;    //오른쪽 끝으로
                }
                for (int i = 0; i < mlist.size(); i++) {
                    Missile m = mlist.get(i);
                    m.setMy(m.getMy() - 5); //y좌표 감소 처리
                    if (m.getMx() < 0) {
                        mlist.remove(i);    //y좌표=0 이면 리스트에서 제거
                    }
                    //충돌여부 판정 → 두 이미지가 겹칠 때
                    //타겟(이미지)의 사각영역
                    Rect rect1 = new Rect(ex, ey, ex + enemyWidth, ey + enemyHeight);
                    //총알(이미지)의 사각영역
                    Rect rect2 = new Rect(m.getMx(), m.getMy(), m.getMx() + missileWidth, m.getMy() + missileHeight);
                    if (rect1.intersect(rect2)) {   //겹친 부분이 있으면
                        hit.start();    //폭발음 플레이
                        isHit = true;   //충돌상태로 변경
                        point += 100;   //점수 증가

                        //충돌한 좌표를 저장
                        hx = ex;
                        hy = ey;
                        mlist.remove(i);    //총알 리스트에서 제거
                        ex = width - enemyWidth;    //타겟의 좌표 초기화
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                postInvalidate();   //화면 갱신 → onDraw() 호출됨
            }
        }

        @Override   // 화면 사이즈 변경될 때(최초 표시, 가로/세로 전환)
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            //화면의 가로세로 크기
            width = getWidth();
            height = getHeight();

            //각 이미지의 가로세로 크기
            gunshipWidth = gunship.getIntrinsicWidth();
            gunshipHeight = gunship.getIntrinsicHeight();
            missileWidth = missile.getIntrinsicWidth();
            missileHeight = missile.getIntrinsicHeight();
            enemyWidth = enemy.getIntrinsicWidth();
            enemyHeight = enemy.getIntrinsicHeight();
            hitWidth = explosure.getIntrinsicWidth();
            hitHeight = explosure.getIntrinsicHeight();

            //비행기 좌표
            x = width / 2 - gunshipWidth / 2;
            y = height - 50;

            //총알 좌표
            mx = x + 20;
            my = y;

            //타겟 좌표
            ex = width - enemyWidth;
            ey = 50;
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            //배경이미지 출력 → setBounds(x1,y1,x2,y2) 영역 지정
            backImg.setBounds(0, 0, width, height);
            backImg.draw(canvas);   //배경이미지를 캔버스에 출력시킴

            //비행기 출력
            gunship.setBounds(x, y, x + gunshipWidth, y + gunshipHeight);
            gunship.draw(canvas);

            //타겟 출력
            if (isHit) {
                //충돌 상태 → 폭발 이미지 출력
                explosure.setBounds(hx - 20, hy - 20, hx + hitWidth - 20, hy + hitHeight - 20);
                explosure.draw(canvas);
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isHit = false;  //충돌하지 않은 상태(초기상태)로 전환
            } else {    //충돌하지 않은 상태
                enemy.setBounds(ex, ey, ex + enemyWidth, ey + enemyHeight);
                enemy.draw(canvas);
            }

            //총알 출력
            for (int i = 0; i < mlist.size(); i++) {
                Missile m = mlist.get(i);
                //총알 이미지의 출력 범위
                missile.setBounds(m.getMx(), m.getMy(), m.getMx() + missileWidth, m.getMy() + missileHeight);
                missile.draw(canvas);   //총알 이미지 출력
            }

            //점수 출력
            String str = "Point: " + point;
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(25);

            //텍스트 출력 drawText(문자열,x,y,페인트객체)
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
            postInvalidate();   //그래픽 갱신요청 → onDraw() 호출
            return super.onKeyDown(keyCode, event);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            isFire = true;  //발사상태로 전환
            fire.start();   //사운드 플레이
            Missile ms = new Missile(x + gunshipWidth / 2, y);  //총알 객체 생성
            mlist.add(ms);  //리스트에 총알 추가
            return super.onTouchEvent(event);
        }
    }
}