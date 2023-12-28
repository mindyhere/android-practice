package com.example.cafemanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpinnerActivity extends AppCompatActivity {
    Spinner spinner;
    Context context;

    List<CategoryDTO> dto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> group = Arrays.asList(getResources().getStringArray(R.array.group));
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item) {
            @Override
            public int getCount() {
                return group.size() - 1;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        List<CategoryDTO> dto = new ArrayList<>();
                        String category = spinner.getSelectedItem().toString();
                        String categoryId = "";
                        if (position == group.size()) {
                            Toast.makeText(context, "분류를 선택해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            switch (position) {
                                case 0:
                                    categoryId = "cf";
//                                    dto.add(categoryId,category);
                                    break;

                                case 1:
                                    categoryId = "bv";
                                    break;
                                case 2:
                                    categoryId = "te";
                                    break;
                                case 3:
                                    categoryId = "fd";
                                    break;
                            }
                        }
                        spinnerAdapter.add(String.valueOf(new CategoryDTO(categoryId, category)));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.d("MyTag", "onNothingSelected");
                    }
                }
        );
    }
}