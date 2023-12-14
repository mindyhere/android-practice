package com.example.graphic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }

    public void onClick(View v) {

        Intent intent = null;

        if (v.getId() == R.id.button1) {

            intent = new Intent(this, ProgressActivity.class);

        } else if (v.getId() == R.id.button2) {

            intent = new Intent(this, ShapeActivity.class);

        } else if (v.getId() == R.id.button3) {

            intent = new Intent(this, KeyActivity.class);

        } else if (v.getId() == R.id.button4) {

            intent = new Intent(this, LineActivity.class);

        } else if (v.getId() == R.id.button5) {

            intent = new Intent(this, GameActivity.class);

        } else if (v.getId() == R.id.button6) {
            intent = new Intent(this, ImageRotateActivity.class);
        } else if (v.getId() == R.id.button7) {
            intent = new Intent(this, MoveActivity.class);
        } else if (v.getId() == R.id.button8) {
            intent = new Intent(this, ClockActivity.class);
        } else if (v.getId() == R.id.button9) {
            intent = new Intent(this, MultiTouchActivity.class);
        }
        startActivity(intent);

    }

}

