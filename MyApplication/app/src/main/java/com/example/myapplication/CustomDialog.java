package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

//    Manifest -> android:theme="@style/Theme.MyDialog" 추가해서 다이얼로그 형태로 띄우기
//    themes.xml 에 커스텀테마 작성(Theme.MyDialog)
public class CustomDialog extends Dialog implements View.OnClickListener {
    Context context;
    EditText editCategory, editId;
    Button btnCancel, btnAdd;
    ImageView iv;
    //    CustomDialogListener listener;
    MenuDAO menuDao;
    List<MenuDTO> items;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 타이틀 미사용, setContentView 보다 먼저 선언되어야 한다.
        setContentView(R.layout.activity_custom_dialog);

        //커스텀 다이얼로그 밖의 화면을 흐리게 설정
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        editCategory = findViewById(R.id.editCategory);
        editId = findViewById(R.id.editId);
        iv = findViewById(R.id.iv);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

//    interface CustomDialogListener {
//        void onAddClicked(String category, String categoryId);
//
//        void onCancelClicked();
//    }

//    public void setDialogListener(CustomDialogListener listener) {
//        this.listener = listener;
//    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCancel) {
            Toast.makeText(context, "이전 화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
            cancel();
        } else if (v.getId() == R.id.btnAdd) {
            String categoryId = editId.getText().toString();
            String category = editCategory.getText().toString();
            if (TextUtils.isEmpty(editId.getText()) || TextUtils.isEmpty(editCategory.getText())) {
                Toast.makeText(context, "카테고리 정보를 확인해주세요.", Toast.LENGTH_SHORT).show();
                editCategory.requestFocus();
            } else {
                MenuDTO dto = new MenuDTO(categoryId, category);
                dto.setCategoryId(categoryId);
                dto.setCategory(category);
                menuDao.addCategory(dto);
                Toast.makeText(context, "카테고리를 추가하였습니다.(" + category + ")", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        } else if (v.getId() == R.id.iv) {
            editCategory.setText("");
            editId.setText("");
            editCategory.requestFocus();
        }
    }
//    public void hideKeyboard(View view) {
//        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//    }
}
