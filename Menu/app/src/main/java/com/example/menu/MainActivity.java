package com.example.menu;

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
        if (v.getId() == R.id.btn1) {
            intent = new Intent(this, OptionMenuActivity.class);
        } else if (v.getId() == R.id.btn2) {
            intent = new Intent(this, OptionXmlActivity.class);
       // } else if (v.getId() == R.id.btn3) {
         //   intent = new Intent(this, );

        }
        startActivity(intent);
    }
}