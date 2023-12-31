package com.example.myapplication;

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
    List<MenuDTO> items;
    static String option = "";

    public MyRecyclerAdapter(Context context, List<MenuDTO> menus, String option) {
        this.context = context;
        this.items = menus;
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
        MenuDTO menuDto = items.get(position);
        holder.tvCategory.setText(menuDto.getCategory());
        holder.tvMenuName.setText(menuDto.getMenuName());
        holder.tvPrice.setText(menuDto.getPrice() + "");
        if (menuDto.getRun() == 1) {
            holder.tvRun.setText("○");
        } else {
            holder.tvRun.setText("Ⅹ");
        }
    }

    @Override
    public int getItemCount() {
        Log.i("test", "상품개수: " + items.size() + "");
        return (null != items ? items.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategory, tvMenuName, tvPrice, tvRun;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategory = itemView.findViewById(R.id.tvCategory);
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
                                            MenuDTO menuDto = items.get(getLayoutPosition());
                                            intent.putExtra("dto", menuDto);
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
                                            items.remove(getLayoutPosition());
                                            notifyItemRemoved(getLayoutPosition());
                                            notifyItemRangeChanged(getLayoutPosition(), items.size());
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
