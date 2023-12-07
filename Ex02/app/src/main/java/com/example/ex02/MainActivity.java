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
        } else if (v.getId() == R.id.button2) {
            intent = new Intent(this, ListXml.class);
        } else if (v.getId() == R.id.button3) {
            intent = new Intent(this, ListArray.class);
        } else if (v.getId() == R.id.button4) {
            intent = new Intent(this, PhonebookActivity.class);
        } else if (v.getId() == R.id.button7) {
            intent = new Intent(this, SpinnerActivity.class);
        }
        else if (v.getId() == R.id.button8) {
            intent = new Intent(this, GridActivity.class);
        }
        else if (v.getId() == R.id.button9) {
            intent = new Intent(this, AutoActivity.class);
        }
        startActivity(intent);
    }
}