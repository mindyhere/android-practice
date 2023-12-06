package com.example.ex02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class ListXml extends AppCompatActivity {
    TextView txtResult;
    List<String> items;
    RecyclerView rv;
    RecyclerView.Adapter myAdapter; //리스트데이터 → 어댑터 → 리사이클러뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_demo1); //화면파일 연결
        txtResult = findViewById(R.id.txtResult);
        rv = findViewById(R.id.rv);

        //화면배치 방법
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false)); //RecyclerView의 화면배치
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL)); //아이템 구분선 설정

        //Arrays.asList → 파일의 자료를 ArrayList로 읽음
        items = Arrays.asList(getResources().getStringArray(R.array.fruit));
        //                      res폴더         스트링배열 (values/array.xml)

        //어댑터 생성&설정
        myAdapter = new ListXml.MyAdapter(items);
        rv.setAdapter(myAdapter);
    }

    //child view를 생성
    class MyAdapter extends RecyclerView.Adapter<ListXml.MyAdapter.ViewHolder> {
        private List<String> data;

        public MyAdapter(List<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list1_row, parent, false);
            return new ListXml.MyAdapter.ViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(@NonNull ListXml.MyAdapter.ViewHolder holder, int position) {
            holder.textView.setText(this.data.get(position));
        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                this.textView = itemView.findViewById(R.id.text1);
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "position: " + getLayoutPosition() + ", text: " + this.textView.getText(), Toast.LENGTH_SHORT).show();
                txtResult.setText(items.get(getLayoutPosition()));
            }
        }
    }
}