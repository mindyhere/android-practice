package com.example.ex02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class AutoActivity extends AppCompatActivity {
    TextView txtResult;
    AutoCompleteTextView auto1;
    String[] items = {"java", "java1", "java2", "java3", "c", "c++", "visual c++", "android", "apple"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        txtResult = (TextView) findViewById(R.id.txtResult);
        auto1 = (AutoCompleteTextView) findViewById(R.id.auto1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        auto1.setAdapter(adapter);

        auto1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                            // 형변환 ← obj타입: 검색한 목록 가져오기 (인덱스)
                txtResult.setText(item);
            }
        });
    }
}