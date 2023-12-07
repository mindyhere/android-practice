package com.example.ex02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PhonebookActivity extends AppCompatActivity {
    TextView txtResult;
    List<PhonebookDTO> items;
    Uri number; //전화번호 변수
    RecyclerView rv;
    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_demo1);

        txtResult = findViewById(R.id.txtResult);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

        items = new ArrayList<>();
        items.add(new PhonebookDTO("김철수", "010-111-2222"));
        items.add(new PhonebookDTO("홍철수", "010-222-3333"));
        items.add(new PhonebookDTO("박철호", "010-333-4444"));
        items.add(new PhonebookDTO("김영희", "010-444-5555"));

        myAdapter = new MyAdapter();
        rv.setAdapter(myAdapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.phonebook, parent, false);
            return new ViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txtName.setText(items.get(position).getName());
            holder.txtTel.setText(items.get(position).getTel());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView txtName, txtTel;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                txtName = itemView.findViewById(R.id.txtName);
                txtTel = itemView.findViewById(R.id.txtTel);
            }

            @Override
            public void onClick(View v) {
                number = Uri.parse("tel: " + items.get(getLayoutPosition()).getTel());
                // Uri.parse: 입력한 스트링을 uri형식으로 변환
                // tel: 전화번호 → 전화번호 형식 (ex. tel: 010-111-2222)
                check();
            }
        }
    }

    void check() {
        int check = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        // 전화걸기 권한 체크

        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL, number);
            // 안드로이드 내장 액티비티(전화걸기 화면)
            startActivity(intent);  //화면이동
        }
    }

    @Override   // onRequestPermissionsResult(요청코드, 권한리스트, 허용여부)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) { //요청코드 검사
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //권한 허용상태 이면
                    check();
                    Log.d("test", "ok");
                } else {
                    Log.d("test", "Permission deny");
                }
                break;
        }
    }
}