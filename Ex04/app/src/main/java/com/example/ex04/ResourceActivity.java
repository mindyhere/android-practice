package com.example.ex04;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ResourceActivity extends AppCompatActivity {
    List<String> items;
    RecyclerView rv;
    RecyclerView.Adapter myAdapter;
    TextView txtResult;

    //리스트 → (어댑터) → RecyclerView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        txtResult = findViewById(R.id.txtResult);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        items = new ArrayList<>();

        try {
            InputStream is = getResources().openRawResource(R.raw.words);
//                              res디렉토리    raw디렉토리     words.xml (→ res/raw/words.xml)
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            //xml 분석기능
            Document doc = builder.parse(is, null);
            //xml문서                    추출
            NodeList words = doc.getElementsByTagName("word");
            //     리스트   ←      태그이름 word
            for (int i = 0; i < words.getLength(); i++) {
                                //태그 개수
                items.add(((Element) words.item(i)).getAttribute("value"));
                //                     목록 아이템    ←    태그 속성값
                //(예)item.add("사과") : 리스트.add(아이템)
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        myAdapter = new MyAdapter();
        rv.setAdapter(myAdapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> { // → 내부클래스
        @NonNull
        @Override         //child view 생성 기능
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new MyAdapter.ViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                            //뷰홀더(holder)-child view 개별행 매핑
            holder.text1.setText(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size(); // →출력할 데이터 건수
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView text1;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                text1 = itemView.findViewById(R.id.text1);
            }

            @Override
            public void onClick(View v) {
                //Toast.makeText(화면, 메세지, 옵션).show()
                Toast.makeText(v.getContext(), "position: " + getLayoutPosition() + ", text: " + text1.getText(), Toast.LENGTH_SHORT).show();
                txtResult.setText(items.get(getLayoutPosition()));
                //     뷰.setText(텍스트)           리스트 아이템의 인덱스
                //  리스트.get(인덱스)
            }
        }
    }
}