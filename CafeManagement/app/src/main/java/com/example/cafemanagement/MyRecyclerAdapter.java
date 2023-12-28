package com.example.cafemanagement;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    Context context;
    List<MenuDTO> menus;
    static String option = "";

    public MyRecyclerAdapter(Context context, List<MenuDTO> menus, String option) {
        this.context = context;
        this.menus = menus;
        this.option = option;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row, parent, false);
        return new MyRecyclerAdapter.ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuDTO dto = menus.get(position);
        holder.tvCategory.setText(dto.getCategory());
//        holder.tvMenuNo.setText(dto.getMenuNo() + "");
        holder.tvMenuName.setText(dto.getMenuName());
        holder.tvPrice.setText(dto.getPrice() + "");
        if (dto.getRun() == 1) {
            holder.tvRun.setText("○");
        } else {
            holder.tvRun.setText("Ⅹ");
        }
    }


    @Override
    public int getItemCount() {
        Log.i("test", "상품개수: " + menus.size() + "");
        return (null != menus ? menus.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategory, tvMenuName, tvPrice, tvRun;
//        private TextView tvMenuNo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategory = itemView.findViewById(R.id.tvCategory);
//            tvMenuNo = itemView.findViewById(R.id.tvMenuNo);
            tvMenuName = itemView.findViewById(R.id.tvMenuName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvRun = itemView.findViewById(R.id.tvRun);


            tvMenuName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    switch (MyRecyclerAdapter.option) {
                        case "edit":
                            builder.setTitle("Check")
                                    .setMessage("선택하신 메뉴를 수정하시겠습니까?")
                                    .setNegativeButton("No", null)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(context, MenuEditActivity.class);
                                            MenuDTO dto = menus.get(getLayoutPosition());
                                            intent.putExtra("dto", dto);
                                            context.startActivity(intent);
                                        }
                                    })
                                    .show();
                            break;
                        case "delete":
                            builder.setTitle("Check")
                                    .setMessage("선택하신 메뉴를 삭제하시겠습니까?")
                                    .setNegativeButton("No", null)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            menus.remove(getLayoutPosition());
                                            notifyItemRemoved(getLayoutPosition());
                                            notifyItemRangeChanged(getLayoutPosition(), menus.size());
                                        }
                                    })
                                    .show();
                            break;
                    }
                }
            });
        }
    }
}
