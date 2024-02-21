package com.example.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {
    ArrayList<BookDTO> list;
    RecyclerView rv;
    RecyclerView.Adapter myAdapter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            myAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        myAdapter = new MyAdapter();
        rv.setAdapter(myAdapter);

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    list = new ArrayList<>();
                    URL url = new URL(Common.SERVER_URL + "/mobile/book_servlet/xml.do");
                    XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                    // xml해석기
                    InputStream is = url.openStream();
                    parser.setInput(is, "utf-8");
                    int eventType = parser.getEventType();
                    String tag;
                    BookDTO dto = null;
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                tag = parser.getName();
                                if (tag.equals("book_name")) {
                                    dto = new BookDTO();
                                    dto.setBook_name(parser.nextText());
                                }
                                if (tag.equals("press")) {
                                    dto.setPress(parser.nextText());
                                }
                                break;

                            case XmlPullParser.END_TAG: // 닫는 태그
                                tag = parser.getName();
                                if (tag.equals("book")) {
                                    list.add(dto);
                                    dto = null;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    Log.i("test", "book_list: " + list);
                    Log.i("test", "size: " + list.size());
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row, parent, false);
            return new MyAdapter.ViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
            holder.txtBookName.setText(list.get(position).getBook_name());
            holder.txtPress.setText(list.get(position).getPress());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView txtBookName, txtPress;

            public ViewHolder(@NonNull View view) {
                super(view);
                view.setOnClickListener(this);
                txtBookName = view.findViewById(R.id.book_name);
                txtPress = view.findViewById(R.id.press);
            }

            @Override
            public void onClick(View v) {

            }
        }
    }
}