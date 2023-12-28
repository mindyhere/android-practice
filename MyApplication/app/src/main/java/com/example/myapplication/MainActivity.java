package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    RecyclerView rv;
    List<PersonDTO> itemList;
    EditText editNum, editName;
    MyRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNum = findViewById(R.id.editNum);
        editName = findViewById(R.id.editName);

        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new MyRecyclerAdapter(MainActivity.this, itemList);
        rv.setAdapter(adapter);
        itemList = new ArrayList<>();

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(editNum.getText().toString());
                String name = editName.getText().toString();

                adapter.itemList.add(new PersonDTO(num, name));
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new MyRecyclerAdapter(MainActivity.this, itemList);
        rv.setAdapter(adapter);
    }
}