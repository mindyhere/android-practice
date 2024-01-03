package com.example.cafemanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuBoardActivity extends AppCompatActivity {
    ImageView iv;
    Button btnAdd, btnHome, btnConfirm;
    AutoCompleteTextView auto;
    RecyclerView rv;
    MenuDAO menuDao;
    List<MenuDTO> items;
    MyRecyclerAdapter adapterR;
    String[] group = {"all", "coffee", "beverage", "tea", "food"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_board);

        iv = findViewById(R.id.iv);
        auto = findViewById(R.id.auto);
        btnAdd = findViewById(R.id.btnAdd);
        btnHome = findViewById(R.id.btnHome);
        btnConfirm = findViewById(R.id.btnConfirm);
        rv = findViewById(R.id.rv);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        menuDao = new MenuDAO(MenuBoardActivity.this);
        adapterR = new MyRecyclerAdapter(MenuBoardActivity.this, items, "edit");
        rv.setAdapter(adapterR);
        items = new ArrayList<>(menuDao.list());

        ArrayAdapter<String> adapterA = new ArrayAdapter<String>(MenuBoardActivity.this, android.R.layout.simple_spinner_dropdown_item, group);
        auto.setAdapter(adapterA);
        auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String keyword = (String) parent.getItemAtPosition(position);
                items = menuDao.list(keyword);
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items = menuDao.list();
                adapterR = new MyRecyclerAdapter(MenuBoardActivity.this, items, "edit");
                rv.setAdapter(adapterR);
            }
        });

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

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = auto.getText().toString();
                if (TextUtils.isEmpty(auto.getText())) {
                    Toast.makeText(MenuBoardActivity.this, "분류/상품명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    items = menuDao.list(keyword);
                    Log.i("test", "상품목록 onResume():" + items);
                    adapterR = new MyRecyclerAdapter(MenuBoardActivity.this, items, "edit");
                    adapterR.notifyDataSetChanged();
                    rv.setAdapter(adapterR);
                    auto.setText("");
                    hidekeyboard();
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        items = menuDao.list(auto.getText().toString());
        adapterR = new MyRecyclerAdapter(MenuBoardActivity.this, items, "edit");
        rv.setAdapter(adapterR);
    }

    public void hidekeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}