package com.example.cafemanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MenuAddActivity extends AppCompatActivity {
    TextView tvTitle;
    Spinner spinner;
    EditText addMenuName, addPrice;
    Button btnSave, btnComplete;
    MenuDAO dao;
    RecyclerView rv;
    MyRecyclerAdapter adapter;
    ArrayAdapter spinnerAdapter;
    List<MenuDTO> menus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add);

        tvTitle = findViewById(R.id.tvTitle);
        spinner = findViewById(R.id.spinner);
        addMenuName = findViewById(R.id.addMenuName);
        addPrice = findViewById(R.id.addPrice);
        List<String> group = Arrays.asList(getResources().getStringArray(R.array.group));
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);

        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

        adapter = new MyRecyclerAdapter(MenuAddActivity.this, menus, "delete");
        rv.setAdapter(adapter);
        menus = new ArrayList<>();

        btnSave = findViewById(R.id.btnSave);
        btnComplete = findViewById(R.id.btnComplete);
        dao = new MenuDAO(this);

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("**TEST", "Title on Click");
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuAddActivity.this);
                builder.setTitle("Check")
                        .setMessage("작업을 취소하시겠습니까?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MenuAddActivity.this, MenuActivity.class);
                                Toast.makeText(MenuAddActivity.this, "이전 화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("No", null);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    spinner.setOnItemSelectedListener();
                    String category = spinner.getSelectedItem().toString();
//                    String categoryId=
                    String menuName = addMenuName.getText().toString();
                    if (TextUtils.isEmpty(addMenuName.getText())) {
                        Toast.makeText(MenuAddActivity.this, "상품명을 입력하세요.", Toast.LENGTH_SHORT).show();
                        addMenuName.requestFocus();
                        return;
                    }
                    int price = Integer.parseInt(addPrice.getText().toString());
                    int run = 1;
                    adapter.menus.add(new MenuDTO(category, menuName, price, run));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MenuAddActivity.this, "메뉴 추가", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MenuAddActivity.this, "상품정보를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    addMenuName.requestFocus();
                }
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (menus.size() == 0) {
                        Toast.makeText(MenuAddActivity.this, "새로 추가한 메뉴가 없습니다.", Toast.LENGTH_SHORT).show();
                        addMenuName.requestFocus();
                    } else {
                        for (int i = 0; i < menus.size(); i++) {
                            dao.insert(menus.get(i));
                        }
                        Toast.makeText(MenuAddActivity.this, "메뉴등록 완료 : " + menus.size() + "개", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("***test", "btnComplete" + menus.size());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new MyRecyclerAdapter(MenuAddActivity.this, menus, "delete");
        rv.setAdapter(adapter);
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

