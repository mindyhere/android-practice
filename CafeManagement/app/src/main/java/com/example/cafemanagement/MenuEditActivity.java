package com.example.cafemanagement;

import static java.lang.Integer.valueOf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuEditActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner;
    EditText editMenuNo, editMenuName, editPrice;
    CheckBox chkRun;
    Button btnUpdate, btnDelete;
    MenuDAO dao;
    MenuDTO dto;
    int run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_edit);
        spinner = findViewById(R.id.spinner);
        editMenuNo = findViewById(R.id.editMenuNo);
        editMenuName = findViewById(R.id.editMenuName);
        editPrice = findViewById(R.id.editPrice);
        chkRun = findViewById(R.id.chkRun);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        chkRun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkRun.isChecked()) {
                    run = 1;
                } else {
                    run = 0;
                }
            }
        });


        dao = new MenuDAO(this);
        Intent intent = getIntent();
        dto = (MenuDTO) intent.getSerializableExtra("dto");
        editMenuNo.setText(Integer.toString(dto.getMenuNo()));
        editMenuName.setText(dto.getMenuName());
        editPrice.setText(Integer.toString(dto.getPrice()));
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        if (valueOf(dto.getRun()) == 1) {
            chkRun.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnUpdate) {
            try {
                String category = spinner.getSelectedItem().toString();
                int menuNo = Integer.parseInt(editMenuNo.getText().toString());
                String menuName = editMenuName.getText().toString();
                if (TextUtils.isEmpty(editMenuName.getText())) {
                    Toast.makeText(MenuEditActivity.this, "상품명을 입력하세요.", Toast.LENGTH_SHORT).show();
                    editMenuName.requestFocus();
                    return;
                }
                int price = Integer.parseInt(editPrice.getText().toString());

                dto.setCategory(category);
                dto.setMenuNo(menuNo);
                dto.setMenuName(menuName);
                dto.setPrice(price);
                dto.setRun(run);
                dao.update(dto);
                Toast.makeText(MenuEditActivity.this, "메뉴수정 완료", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MenuEditActivity.this, "상품정보를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btnDelete) {
            new AlertDialog.Builder(this)
                    .setTitle("삭제 확인")
                    .setMessage("선택하신 메뉴를 삭제하시겠습니까?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.delete(dto.getMenuNo());
                            Toast.makeText(MenuEditActivity.this, "메뉴삭제 완료", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
}