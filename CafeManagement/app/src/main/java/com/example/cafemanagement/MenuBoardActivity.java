package com.example.cafemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuBoardActivity extends AppCompatActivity {
    Button btnAdd, btnHome;
    //    Button btnSearch;
//    EditText editSearch;
    RecyclerView rv;
    MenuDAO menuDao;
    List<MenuDTO> items;
    MyRecyclerAdapter adapterR;
    Spinner spinner;
//    boolean checkFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_board);

//        editSearch = findViewById(R.id.editSerch);
        btnAdd = findViewById(R.id.btnAdd);
        btnHome = findViewById(R.id.btnHome);
//        btnSearch = findViewById(R.id.btnSearch);
        rv = findViewById(R.id.rv);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
//        adapterR = new MyRecyclerAdapter(MenuBoardActivity.this, items, "edit");

        spinner = findViewById(R.id.spinner);
        items = new ArrayList<>();
        menuDao = new MenuDAO(this);
//        분류별 목록조회
        List<String> group = Arrays.asList(getResources().getStringArray(R.array.group));
        ArrayAdapter adapterS = new ArrayAdapter(MenuBoardActivity.this, android.R.layout.simple_spinner_item, group);
        spinner.setAdapter(adapterS);
        spinner.setSelection(group.size() - 1);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        if (checkFirst) {
//                            spinner.setSelection(group.size() - 1);
//                            checkFirst = false;
//                            Log.i("test", "상품목록조회" + spinner.getSelectedItemPosition() + "번");
//                        } else {
                        adapterR = new MyRecyclerAdapter(MenuBoardActivity.this, items, "edit");
                        rv.setAdapter(adapterR);
                        items = menuDao.list(position);
                        adapterR.notifyDataSetChanged();
                        Log.i("test", "여기는 스피너set" + position + "번 항목선택" + " + " + spinner.getSelectedItemPosition() + " + " + items.size() + "개");
//                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuBoardActivity.this, "처음화면으로 들아갑니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuBoardActivity.this, MenuAddActivity.class);
                Toast.makeText(MenuBoardActivity.this, "신규등록을 위해 상품정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

//    protected void onResume() {
//        super.onResume();
//        items = menuDao.list();
//        Log.i("test", "상품목록 onResume():" + items);
//        adapterR = new MyRecyclerAdapter(MenuBoardActivity.this, items, "edit");
//        rv.setAdapter(adapterR);
//    }
}