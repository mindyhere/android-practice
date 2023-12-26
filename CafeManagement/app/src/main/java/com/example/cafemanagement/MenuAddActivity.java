package com.example.cafemanagement;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MenuAddActivity extends AppCompatActivity {
    Spinner spinner;
    EditText addMenuNo, addMenuName, addPrice;
    Button btnSave;
    MenuDAO dao;
    RecyclerView rv;
    RecyclerView.Adapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add);

        dao = new MenuDAO(this);
        spinner = findViewById(R.id.spinner);
        addMenuNo = findViewById(R.id.addMenuNo);
        addMenuName = findViewById(R.id.addMenuName);
        addPrice = findViewById(R.id.addPrice);
        btnSave = findViewById(R.id.btnSave);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(),DividerItemDecoration.VERTICAL));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String category = spinner.getSelectedItem().toString();
                    int menuNo = Integer.parseInt(addMenuNo.getText().toString());
                    String menuName = addMenuName.getText().toString();
                    if (TextUtils.isEmpty(addMenuName.getText())) {
                        Toast.makeText(MenuAddActivity.this, "상품명을 입력하세요.", Toast.LENGTH_SHORT).show();
                        addMenuName.requestFocus();
                        return;
                    }
                    int price = Integer.parseInt(addPrice.getText().toString());
                    int run = 1;

                    MenuDTO dto = new MenuDTO(category, menuNo, menuName, price, run);
                    dao.insert(dto);
                    Toast.makeText(MenuAddActivity.this, "메뉴등록 완료", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MenuAddActivity.this, "상품정보를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    addMenuNo.requestFocus();
                }
            }
        });
    }
}

