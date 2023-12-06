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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ListDemo1 extends AppCompatActivity {
    TextView txtResult;
    String[] items = {"사과", "포도", "레몬", "수박", "바나나", "체리"};
    RecyclerView rv;    //목록
    RecyclerView.Adapter myAdapter;
    //         어댑터(변환기): 배열데이터 → 어댑터 → 목록

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_demo1);

        //화면 위젯 주소값 연결
        txtResult = findViewById(R.id.txtResult);
        rv = findViewById(R.id.rv);

        //RecyclerView의 화면배치 설정
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));    //목록 구분선 설정

        //Adapter 생성 및 설정
        myAdapter = new MyAdapter(items);
        rv.setAdapter(myAdapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private String[] data;

        public MyAdapter(String[] data) {
            //  items의 타입이 배열 → data도 동일한 타입으로 지정
            this.data = data;
        }   //생성자

        @NonNull
        @Override   // child view 를 생성하는 코드
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list1_row, parent, false);
            //             LayoutInflater 화면생성                           list1_row.xml 레이아웃파일
            return new ViewHolder(rowItem);
        }

        @Override   // 각각의 세부 아이템을 child view에 연결시키는 코드
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
            holder.textView.setText(this.data[position]);
        }

        @Override
        public int getItemCount() {
            return this.data.length;    //집합데이터(아이템) 개수
        }

        //RecyclerView → 재생뷰
        //child view → 개별행
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            //child view(ViewHolder)의 화면구성 및 이벤트 처리
            private TextView textView;  //지역변수 선언

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                this.textView = itemView.findViewById(R.id.text1);
            }   //내부클래스 ViewHolder의 생성자 오버로딩

            @Override   //View.OnClickListener 인터페이스 추상메서드 onClick()을 구현
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "position: " + getLayoutPosition() + ", text: " + this.textView.getText(), Toast.LENGTH_SHORT).show();
                //Context 현재 실행중인 화면 → getContext() 가져오기                                                                       Toast.LENGTH_SHORT 1~2초
                //Toast.makeText(화면, 메세지, 옵션).show                                                                                Toast.LENGTH_LONG 2~3초
                txtResult.setText(items[getLayoutPosition()]);
            }
        }
    }
}