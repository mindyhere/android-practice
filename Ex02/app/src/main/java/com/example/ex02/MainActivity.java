package com.example.ex02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
            intent = new Intent(this, ListDemo1.class);
        }
        else if (v.getId() == R.id.button2) {
            intent = new Intent(this, ListXml.class);
        }
        startActivity(intent);
    }
}