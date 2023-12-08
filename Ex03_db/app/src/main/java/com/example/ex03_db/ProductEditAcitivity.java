package com.example.ex03_db;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InsertGesture;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProductEditAcitivity extends AppCompatActivity implements View.OnClickListener {
    EditText editProductName, editPrice, editAmount;
    Button btnUpdate, btnDelete;
    ProductDAO dao;
    ProductDTO dto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit_acitivity);
        editProductName = findViewById(R.id.editProductName);
        editPrice = findViewById(R.id.editPrice);
        editAmount = findViewById(R.id.editAmount);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        dao = new ProductDAO(this);
        Intent intent = getIntent();
        dto = (ProductDTO) intent.getSerializableExtra("dto");
        editProductName.setText(dto.getProduct_name());
        editPrice.setText(Integer.toString(dto.getPrice()));
        editAmount.setText(dto.getAmount() + "");
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnUpdate) {
            String product_name = editProductName.getText().toString();
            int price = Integer.parseInt(editPrice.getText().toString());
            int amount = Integer.parseInt(editAmount.getText().toString());
            dto.setProduct_name(product_name);
            dto.setPrice(price);
            dto.setAmount(amount);
            dao.update(dto);

            Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
            finish();

        } else if (v.getId() == R.id.btnDelete) {
            new AlertDialog.Builder(this)
                    .setTitle("확인")
                    .setMessage("삭제할까요?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.delete(dto.getId());
                            Toast.makeText(ProductEditAcitivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
}