package com.example.preference;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;


public class EditPreferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 파일을 읽는 과정에서 오류가 발생하여 모두 읽지 못할 수 있으므로 트랜잭션 처리
        getSupportFragmentManager().    //fragment 관리자
                beginTransaction().     //트랜젝션 시작
                replace(android.R.id.content, new MyFragment())     //content → 교체
                .commit();  //확정
    }

    public static class MyFragment extends PreferenceFragmentCompat {
        //fragment 코드조각, 화면의 일부분
        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            addPreferencesFromResource(R.xml.preferences);
            //추가 환경설정파일          리소스
        }
    }
}