package com.example.ex04;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelSaveActivity extends AppCompatActivity {
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
        //다이얼로그 생성
        new AlertDialog.Builder(this)
                .setTitle("엑셀저장")
                .setMessage("자료를 엑셀파일로 저장하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        backup();   //백업메서드 호출(실행)
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

    public void backup() {
        pd = ProgressDialog.show(this, "작업중", "자료를 엑셀로 저장하고 있습니다. 잠시만 기다려 주세요.");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();   //스레드 실행하는 기능
                copy();
                handler.sendEmptyMessage(0);    //화면 고치는 작업
                Looper.loop();
            }
        });
        thread.start();
    }

    Handler handler = new Handler() {
        //sendEmptyMessage 를 받는 함수
        public void handleMessage(Message msg) {
            pd.dismiss();   //프로그레스 종료
            Toast.makeText(ExcelSaveActivity.this, "작업이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    public void copy() {
        WritableWorkbook workbook = null;
        WritableSheet sheet = null;
        String fileName = path + "/test.xls";   //파일경로

        try {
            File file = new File(fileName);  //java.io.*
            workbook = Workbook.createWorkbook(file);   //엑셀파일 생성
            workbook.createSheet("list", 0);
            //                  (시트이름, 인덱스)
            sheet = workbook.getSheet(0);   //첫번째 시트
            ArrayList<PhoneBookDTO> list = ExcelActivity.items;
            Label label = null;
            WritableCellFormat cf = new WritableCellFormat();
            //셀의 형식
            cf.setBorder(Border.ALL, BorderLineStyle.THIN);
            label = new Label(0, 0, "이름", cf);
            sheet.addCell(label);
            label = new Label(1, 0, "전화번호", cf);
            sheet.addCell(label);   //시트에 셀 추가

            for (int i = 0; i < list.size(); i++) {
                PhoneBookDTO dto = list.get(i);

                // new Label(col,row,value)
                label = new Label(0, i + 1, dto.getName(), cf);
                sheet.addCell(label);
                label = new Label(1, i + 1, dto.getHp(), cf);
                sheet.addCell(label);
            }
            workbook.write();   //엑셀파일 저장
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