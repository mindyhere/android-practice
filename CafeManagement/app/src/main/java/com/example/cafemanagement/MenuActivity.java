package com.example.cafemanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuActivity extends AppCompatActivity {
    Button btnAdd;
    RecyclerView rv;
    MenuDAO dao;
    List<MenuDTO> menus;
    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnAdd = findViewById(R.id.btnAdd);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        dao = new MenuDAO(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(MenuActivity.this, MenuAddActivity.class);
//                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        menus = dao.list();
        Log.i("test", "상품목록: " + menus);
        myAdapter = new MyAdapter();
        rv.setAdapter(myAdapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row, parent, false);
            return new ViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MenuDTO dto = menus.get(position);
            Log.i("test", "dto: " + dto);
            holder.tvCategory.setText(dto.getCategory()+"\t");
            holder.tvMenuNo.setText(dto.getMenuNo()+""+"\t");
            holder.tvMenuName.setText(dto.getMenuName()+"\t");
            holder.tvPrice.setText(dto.getPrice()+""+"\t");
            //holder.tvRun.setText(dto.getRun()+"");
            if(dto.getRun()==1) {
                holder.tvRun.setText("판매중");
            } else {
                holder.tvRun.setText("임시중단");
            };
        }

        @Override
        public int getItemCount() {
            Log.i("test", "상품개수: " + menus.size() + "");
            return menus.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView tvCategory, tvMenuNo, tvMenuName, tvPrice, tvRun;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                this.tvCategory = itemView.findViewById(R.id.tvCategory);
                this.tvMenuNo = itemView.findViewById(R.id.tvMenuNo);
                this.tvMenuName = itemView.findViewById(R.id.tvMenuName);
                this.tvPrice = itemView.findViewById(R.id.tvPrice);
                this.tvRun = itemView.findViewById(R.id.tvRun);
            }

            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(MenuActivity.this, MenuEditActivity.class);

            }
        }
    }
}