package com.example.cafemanagement;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter {
    Spinner spinner;
    Context context;

    List<CategoryDTO> dto;
    List<String> group;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    ArrayAdapter spinnerAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, group) {
        @Override
        public int getCount() {
            return group.size() - 1;
        }
    };

    public void onCreate() {
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection();
    }


}
