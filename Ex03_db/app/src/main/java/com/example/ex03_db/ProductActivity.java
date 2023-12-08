package com.example.ex03_db;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ProductActivity extends AppCompatActivity {
    Button btnAdd;
    RecyclerView rv;
    ProductDAO dao;
    List<ProductDTO> items;
    RecyclerView.Adapter myAdapter;

    @Override   //화면생성
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        //위젯요소의 주소값 연결
        btnAdd = findViewById(R.id.btnAdd);
        rv = findViewById(R.id.rv);
        //화면 레이아웃(세로배치) 설정
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

        dao = new ProductDAO(this); //객체 시작 주소값 연결

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, ProductAddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override   //화면 복귀
    protected void onResume() {
        super.onResume();
        items = dao.list();
        Log.i("test", "상품목록: " + items);

        myAdapter = new MyAdapter();  //어댑터생성
        rv.setAdapter(myAdapter);   //리사이클러뷰에 어댑터 연결
    }

    //child view를 생성
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @NonNull
        @Override   //레이아웃 설정
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
            return new ViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProductDTO dto = items.get(position);   //
            Log.i("test", "dto:" + dto);
            holder.txtProductName.setText(dto.getProduct_name());
            holder.txtPrice.setText("가격:" + dto.getPrice() + "원");
            holder.txtAmount.setText("수량:" + dto.getAmount());
        }

        @Override
        public int getItemCount() {
            Log.i("test", "자료개수:" + items.size() + "");
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView txtProductName, txtPrice, txtAmount;

            public ViewHolder(@NonNull View view) {
                super(view);
                view.setOnClickListener(this);
                //chile뷰의 클릭이벤트→현재클래스에서 처리
                this.txtProductName = view.findViewById(R.id.txtProductName);
                this.txtPrice = view.findViewById(R.id.txtPrice);
                this.txtAmount = view.findViewById(R.id.txtAmount);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, ProductEditAcitivity.class);
                ProductDTO dto = items.get(getLayoutPosition());
                intent.putExtra("dto", dto);
                startActivity(intent);
            }
        }
    }
}