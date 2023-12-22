package com.example.cafemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MenuAddActivity extends AppCompatActivity {
    Spinner spinner;
    String[] arrCategory = {"", "coffee", "beverage", "tea", "food"};
    EditText addMenuNo, addMenuName, addPrice;
    Switch switchRun;
    Button btnSave, btnAdd;
    RecyclerView rvList;
    MenuDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add);

        spinner = findViewById(R.id.spinner);
        addMenuNo = findViewById(R.id.addMenuNo);
        addMenuName = findViewById(R.id.addMenuName);
        addPrice = findViewById(R.id.addPrice);
        switchRun = findViewById(R.id.switchRun);
        btnAdd = findViewById(R.id.btnAdd);
        btnSave = findViewById(R.id.btnSave);
        rvList = findViewById(R.id.rvList);
        dao = new MenuDAO(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnTouchListener() {
        }

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (spinner.getSelectedItem() != "") {
                            String category = spinner.getSelectedItem().toString();
                            Toast.makeText(MenuAddActivity.this, arrCategory[position] + "을/를 선택했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        if (spinner.getSelectedItem() == "") {
                            String category = null;
                            Toast.makeText(MenuAddActivity.this, "추가하려는 상품의 분류를 선택해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }
}