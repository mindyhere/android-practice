package com.example.mobileweb;

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
            intent = new Intent(this, BrowserDemo1.class);
        } else if (v.getId() == R.id.button2) {
            intent = new Intent(this, BrowserDemo2.class);
        } else if (v.getId() == R.id.button3) {
            intent = new Intent(this, BrowserDemo3.class);
        } else if (v.getId() == R.id.button4) {
            intent = new Intent(this, BrowserDemo4.class);
        } else if (v.getId() == R.id.button5) {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
    }
}