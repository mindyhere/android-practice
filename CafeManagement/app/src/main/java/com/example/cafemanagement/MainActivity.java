package com.example.cafemanagement;

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
        if (v.getId() == R.id.btnMenu) {
            intent = new Intent(this, MenuActivity.class);
        }
        startActivity(intent);
    }
}