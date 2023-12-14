package com.example.graphic;

import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MultiTouchActivity extends AppCompatActivity {
    //멀티터치 → Ctrl키를 누른 상태에서 화면테스트
    final static float STEP = 200;
    float mRatio = 1.0f;
    int mBaseDist;
    float mBaseRatio;
    ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_touch);
        mImg = (ImageView) findViewById(R.id.img);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 포인터가 2개일 때(멀티터치)
        if (event.getPointerCount() == 2) {
            int action = event.getAction();
            int pureaction = action & MotionEvent.ACTION_MASK;
            //& → bit and
            //public static final int ACTION_MASK= 0xff → 16진수, 255

            //멀티터치 시작
            if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                //두 손가락의 길이 구하기
                mBaseDist = getDistance(event);
                mBaseRatio = mRatio;
            } else {
                //변화된 길이- 처음길이
                float delta = (getDistance(event) - mBaseDist) / STEP;
                float multi = (float) Math.pow(2, delta);
                mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
                Log.i("test", "조절 길이: " + mRatio);
                Matrix m = new Matrix();
                m.postScale(mRatio, mRatio);
                mImg.setImageMatrix(m);
            }
        }
        return true;
    }

    int getDistance(MotionEvent event) {
        //손가락 두개의 좌표 계산
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }
}