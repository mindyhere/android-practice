package com.example.ex02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListArray extends AppCompatActivity {
    //위젯선언(필드변수)
    EditText edit1;
    Button button1;
    List<String> items;
    RecyclerView rv;
    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_array); //화면 레이아웃 지정

        rv = findViewById(R.id.rv); //참조변수와 위젯 연결
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        edit1 = findViewById(R.id.edit1);
        button1 = findViewById(R.id.button1);

        //리스트 아이템 구성
        items = new ArrayList<String>();
        items.add("apple");
        items.add("grape");
        items.add("pineapple");
        items.add("cherry");
        items.add("plum");

        //어댑터 생성(List → 어댑터 → RecyclerView)
        myAdapter = new ListArray.myAdapter();
        //               외부클래스.내부클래스
        rv.setAdapter(myAdapter);   //RecyclerView에 어댑터 연결

        //버튼클릭 이벤트 처리
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edit1.getText().toString();
                items.add(str);
                //EditText에 입력된 값 → 리스트에 항목 추가(스트링)
                myAdapter.notifyDataSetChanged();   //어댑터 갱신(데이터 변경사항 통보 → 화면반영)
            }
        });
    }

    class myAdapter extends RecyclerView.Adapter<ListArray.myAdapter.ViewHolder> {
                                         // 외부클래스.내부클래스.내부클래스
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // LayoutInflater 레이아웃 생성기
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list1_row, parent, false);
            return new ListArray.myAdapter.ViewHolder(rowItem);

        }

        @Override   //바인딩: child view 의 내용을 연결
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(items.get(position));
//          child view 텍스트뷰  내용채움       인덱스(0번~)
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        // 리스트뷰의 개별 아이템 정의
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
            private TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnLongClickListener(this); //뷰 롱클릭 이벤트 등록
                textView = itemView.findViewById(R.id.text1);
            } //생성자 오버로딩

            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "position: " + getLayoutPosition() + ", text: " + textView.getText(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(컨텍스트, 메세지, 옵션).show()
                items.remove(getLayoutPosition());
                //리스트.remove(인덱스) → 리스트에서 제거
                myAdapter.notifyDataSetChanged();   //어댑터에 변경사항 알림
                return true;    //true 메서드 실행.반영(↔ false 무시)
            }
        }
    }
}