package com.example.graphic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
        }
        startActivity(intent);
    }
}