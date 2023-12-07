package com.example.ex02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class GridActivity extends AppCompatActivity {
    //변수선언
    TextView txtResult;
    String[] items = {"사과", "포도", "바나나", "자두", "귤", "자몽", "사과", "포도", "바나나", "자두", "귤", "자몽", "사과", "포도", "바나나", "자두", "귤", "자몽"};
    RecyclerView rv;
    RecyclerView.Adapter myAdapter;

    @Override   //화면생성
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_demo1);   //화면 레이아웃파일 연결
        txtResult = findViewById(R.id.txtResult);
        rv = findViewById(R.id.rv);
        //리소스 연결

        rv.setLayoutManager(new GridLayoutManager(this, 3));
        //리사이클러뷰의 레이아웃 설정 →그리드 레이아웃                 컬럼개수

        // 집합데이터 → 어댑터 → 집합뷰로 변환
        myAdapter = new MyAdapter(items);
        rv.setAdapter(myAdapter);
    }

    //커스텀 Adapter를 정의
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private String[] data;

        public MyAdapter(String[] data) {
            this.data = data;
        }

        @NonNull
        @Override   //뷰 생성
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list1_row, parent, false);
            return new ViewHolder(rowItem);
        }

        @Override   //뷰에 데이터 출력(바인딩)
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(this.data[position]);
        }

        @Override   //데이터의 개수
        public int getItemCount() {
            return this.data.length;
        }

        //child view를 정의
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
                txtResult.setText(items[getLayoutPosition()]);
            }
        }
    }
}