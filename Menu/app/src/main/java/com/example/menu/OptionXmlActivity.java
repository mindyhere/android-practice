package com.example.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OptionXmlActivity extends AppCompatActivity {
    TextView selection;
    String[] items = {"lorem", "ipsum", "dolor", "sit", "amet"};
    RecyclerView rv;
    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_option_menu);
        selection = findViewById(R.id.selection);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        myAdapter = new MyAdapter();
        rv.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu, menu);
        //전개, 생성.  res/menu/menu.xml
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu1) {
            selection.setTextSize(TypedValue.COMPLEX_UNIT_SP, 55);
        } else if (item.getItemId() == R.id.menu2) {
            selection.setTextSize(TypedValue.COMPLEX_UNIT_SP, 65);
        } else if (item.getItemId() == R.id.menu3) {
            selection.setTextSize(TypedValue.COMPLEX_UNIT_SP, 75);
        } else if (item.getItemId() == R.id.menu4) {
            selection.setTextSize(TypedValue.COMPLEX_UNIT_SP, 85);
        } else if (item.getItemId() == R.id.menu5) {
            selection.setTextSize(TypedValue.COMPLEX_UNIT_SP, 95);
        } else if (item.getItemId() == R.id.menu6) {
            selection.setTextSize(TypedValue.COMPLEX_UNIT_SP, 105);
        }
        return true;    //메뉴화면 표시
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new ViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(items[position]);
        }

        @Override
        public int getItemCount() {
            return items.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView textView;

            public ViewHolder(@NonNull View view) {
                super(view);
                view.setOnClickListener(this);
                textView = view.findViewById(R.id.text1);
            }

            @Override
            public void onClick(View v) {
                selection.setText(items[getLayoutPosition()]);
            }
        }
    }
}