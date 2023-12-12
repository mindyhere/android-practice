package com.example.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContextMenuActivity extends AppCompatActivity {
    EditText editName;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menu);
        editName = findViewById(R.id.editName);
        btnOk = findViewById(R.id.btnOk);
        registerForContextMenu(editName);
        //등록      컨텍스트메뉴    대상
        registerForContextMenu(btnOk);
    }

    @Override   //컨텍스트메뉴가 생성될 때 호출
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //View v → 어느 위젯을 눌렀는지
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == editName) {
            menu.setHeaderTitle("입력메뉴");    //메뉴 타이틀
            // menu.add(그룹아이디, 아이템아이디, 출력순서, 제목)
            menu.add(0, 1, 0, "번역");
            menu.add(0, 2, 0, "필기인식");
        } else if (v == btnOk) {
            menu.add(0, 3, 0, "지우기");
        }
    }

    @Override   //메뉴 아이템을 선택할 때
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1: //← 아이템아이디 선택
                Toast.makeText(this, "번역하기", Toast.LENGTH_SHORT).show();
                //Toast.makeText(화면, 메세지, 옵션)
                break;
            case 2:
                Toast.makeText(this, "필기인식", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                editName.setText("");
                break;
        }
        return true;
    }
}