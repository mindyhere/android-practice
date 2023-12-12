package com.example.ex04;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class BackupActivity extends AppCompatActivity {
    //  내부저장소에 저장된 test.txt 파일을 외부저장소에 백업
    ProgressDialog pd;  //진행율 다이얼로그
    long fileSize;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getExternalFilesDir(null).getAbsolutePath();
        //      외부메모리                       절대경로
        showDlg();
    }

    void showDlg() {
        new AlertDialog.Builder(this)   //다이얼로그 대화상자 생성기
                .setTitle("확인")     //대화상자 제목
                .setMessage("백업 하시겠습니까?")   //대화상자의 메세지
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    //오른쪽버튼 → "버튼 텍스트", 동작
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createThreadAndDialog();
                    }
                })
                .setNegativeButton("아니오", null)
                //왼쪽버튼 → "버튼 텍스트", 동작
                .show();
    }

    void createThreadAndDialog() {
        pd = new ProgressDialog(this);
        pd.setTitle("백업");
        pd.setMessage("백업 작업이 진행 중 입니다.");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setProgress(0);
        pd.setMax(100);
        //진행률 바 표시 백그라운드 스레드 추가
        pd.show();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();   //실행준비
                backup();   //백업작업
                handler.sendEmptyMessage(0);    //호출 시 → handleMessage() 에서 처리
                Looper.loop();  //실행
            }
        });
        thread.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            pd.dismiss();   //다이얼로그 종료
            Toast.makeText(BackupActivity.this, "백업이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        }
    };

    void backup() {
        try {
            File src = new File("/data/data/" + getPackageName() + "/files/test.txt");  //내부메모리 파일
            fileSize = src.length();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(src));
            FileOutputStream out = new FileOutputStream(path + "/test.txt");
            byte[] buffer = new byte[1024];
            int data = 0;
            long tot = 0;
            int curProgress = 0;

            while (true) {
                data = in.read(buffer);     //1바이트 씩 버퍼에 저장(최대 1MB)
                if (data == -1) break;      //내용이 없으면 종료
                out.write(buffer, 0, data);
                //  write(버퍼, offset)
                tot += data;    //바이트 수를 누적
                curProgress = (int) (tot * 100 / fileSize);     //진행율 % 표시
                pd.setProgress(curProgress);
            }
            //리소스 정리
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
