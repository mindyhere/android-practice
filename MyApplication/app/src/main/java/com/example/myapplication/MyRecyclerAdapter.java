package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    Context context;
    List<PersonDTO> itemList;

//    int deleteIndex;

    public MyRecyclerAdapter(Context context, List<PersonDTO> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new MyRecyclerAdapter.ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PersonDTO dto = itemList.get(position);

        holder.tvNum.setText(dto.getNum() + "");
        holder.tvName.setText(dto.getName());
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }

//    public void addItem(PersonDTO dto) {
//        itemList.add(dto);
//    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNum;
        TextView tvName;
//        itemView.setOnClickListener(this);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNum = itemView.findViewById(R.id.tvNum);
            tvName = itemView.findViewById(R.id.tvName);
        }

        @Override
        public void onClick(View v) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setTitle("알림")
//                    .setMessage("선택메뉴를 삭제할까요?")
//                    .setNegativeButton("No", null)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            itemList.remove(deleteIndex);
//                            notifyDataSetChanged();
//                        }
//                    })
//                    .show();

        }
    }
}
