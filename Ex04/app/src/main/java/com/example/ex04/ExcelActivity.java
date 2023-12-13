package com.example.ex04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExcelActivity extends AppCompatActivity {
    public static ArrayList<PhoneBookDTO> items;
    RecyclerView rv;
    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_excel);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        input();
        list();
    }

    public void input() {
        items = new ArrayList<PhoneBookDTO>();  //리스트 생성
        PhoneBookDTO dto1 = new PhoneBookDTO();
        dto1.setName("kim");
        dto1.setHp("011-222-3333");
        PhoneBookDTO dto2 = new PhoneBookDTO();
        dto2.setName("lee");
        dto2.setHp("010-555-7777");
        PhoneBookDTO dto3 = new PhoneBookDTO();
        dto3.setName("choi");
        dto3.setHp("010-9999-6543");

        items.add(dto1);
        items.add(dto2);
        items.add(dto3);
    }

    public void list() {
        String[] name = new String[items.size()];
        //리스트.size(): 데이터 개수 → 3개(dto1,2,3)

        for (int i = 0; i < items.size(); i++) {
            PhoneBookDTO dto = items.get(i);
            name[i] = dto.getName() + "(" + dto.getHp() + ")";
        }
        myAdapter = new MyAdapter();
        rv.setAdapter(myAdapter);
    }

    public void onClick(View v) {
        Intent intent = null;
        if (v.getId() == R.id.btnSave) {
            intent = new Intent(this, ExcelSaveActivity.class);
        } else if (v.getId() == R.id.btnLoad) {
            intent = new Intent(this, ExcelLoadActivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        list();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @NonNull
        @Override   //child view 생성
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            //레이아웃 생성기로 생성
            return new MyAdapter.ViewHolder(rowItem);
        }

        @Override   //child view에 내용 출력(바인딩)
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
            holder.text1.setText(items.get(position).getName() + "(" + items.get(position).getHp() + ")");
        }

        @Override   //자료개수
        public int getItemCount() {
            return items.size();
        }

        //child view detail, 이벤트 처리
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView text1;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);  //뷰 클릭 이벤트
                text1 = itemView.findViewById(R.id.text1);
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "position: " + getLayoutPosition() + ", text: " + text1.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}