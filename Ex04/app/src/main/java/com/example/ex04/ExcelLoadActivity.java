package com.example.ex04;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;

public class ExcelLoadActivity extends AppCompatActivity {
    ProgressDialog pd;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //path = getExternalFilesDir(null).getAbsolutePath();     //(1)외부메모리
        path = "/data/data/com.example.ex04/files/";     //(2)내부메모리
        showDlg();
    }

    public void showDlg() {
        new AlertDialog.Builder(this)
                .setTitle("엑셀 불러오기")
                .setMessage("엑셀파일에서 자료를 불러오시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        process();  //복원작업 진행
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();   //현재화면 종료
                    }
                })
                .show();
    }

    public void process() {
        pd = ProgressDialog.show(this, "작업중", "작업중입니다.");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                load();     //엑셀파일 읽어서 리스트 갱신
                handler.sendEmptyMessage(0);    //화면 반영요청
                Looper.loop();
            }
        });
        thread.start();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            pd.dismiss();   //프로그레스 종료
            Toast.makeText(ExcelLoadActivity.this, "작업이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            finish();   //현재화면 종료
        }
    };

    public void load() {
        Workbook workbook = null;
        Sheet sheet = null;
        String fileName = path + "/test.xls";
        try {
            File file = new File(fileName);
            workbook = Workbook.getWorkbook(file);  //java.io.*
            sheet = workbook.getSheet(0);   //첫번째 시트
            ArrayList<PhoneBookDTO> list = new ArrayList<PhoneBookDTO>();
            Label label = null;
            int row = sheet.getRows();

            for (int i = 1; i < row; i++) {
                //1행:행머리글 위치 → 2행부터 실제 데이터가 입력되어 있어 i=1부터 시작
                Log.i("test", "row: " + row);
                Cell[] cell = sheet.getRow(i);  //엑셀에서 i번째 셀
                String name = cell[0].getContents();
                String hp = cell[1].getContents();
                PhoneBookDTO dto = new PhoneBookDTO();
                dto.setName(name);
                dto.setHp(hp);
                list.add(dto);
            }
            //ExcelActivity.java의 static 리스트 갱신
            ExcelActivity.items = list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}