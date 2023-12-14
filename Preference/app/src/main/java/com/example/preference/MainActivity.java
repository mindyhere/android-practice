package com.example.preference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {
    TextView checkbox, txtScreen;
    EditText text;
    Button btnOk;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkbox = findViewById(R.id.checkbox);
        txtScreen = findViewById(R.id.screen);
        text = findViewById(R.id.text);
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = prefs.edit();
                //환경설정 편집 기능
                edit.putString("text", text.getText().toString());
                edit.commit();  //xml 파일에 확정
                Toast.makeText(getApplicationContext(), "환경설정값이 변경되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override   //메뉴생성
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("환경설정").setIntent(new Intent(this, EditPreferences.class));
        //메뉴.추가  타이틀    클릭하면                    현재화면            다음화면 전환
        return super.onCreateOptionsMenu(menu);
    }

    @Override   //화면 복귀
    protected void onResume() {
        super.onResume();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);    //환경설정 처리 기능
        float font_size = Float.parseFloat(prefs.getString("font", "10"));
        //                               환경변수.get자료형(key, default value)
        checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, font_size);
        //체크박스  사이즈설정          sp: 상대적인 픽셀크기
        txtScreen.setTextSize(TypedValue.COMPLEX_UNIT_SP, font_size);

        String color = prefs.getString("font_color", "#ffffff");
        checkbox.setTextColor(Color.parseColor(color));
        txtScreen.setTextColor(Color.parseColor(color));
        boolean ch = prefs.getBoolean("checkbox", false);
        checkbox.setText("" + ch);
        boolean screenCheck = prefs.getBoolean("screenOn", false);
        txtScreen.setText("" + screenCheck);
        if (screenCheck) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //화면 꺼지지 않음
        }
        String str = prefs.getString("text", "");
        text.setText(str);
        text.setTextSize(font_size);
    }
}

/*  내부저장소에 환경설정으로 설정한 값(내용) 파일로 저장됨
위치 → /data/data/com.example.preference/shared_prefs 내 xml파일로 저장된다.
 */