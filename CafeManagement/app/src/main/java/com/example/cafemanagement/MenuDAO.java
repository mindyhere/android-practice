package com.example.cafemanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    Context context;
    SQLiteDatabase db;

    public MenuDAO(Context context) {
        this.context = context;
    }

    public SQLiteDatabase dbConn() {
        db = context.openOrCreateDatabase("cafemanagement.db", Context.MODE_PRIVATE, null);
        String sql = "create table if not exists menuList (category text not null, menu_no integer primary key, menu_name text not null, price integer default 0 not null)";
        db.execSQL(sql);
        return db;
    }

    public void insert(MenuDTO dto) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = String.format("insert into menuList values ('%s', %d, '%s', %d)", dto.getCategory(), dto.getMenuNo(), dto.getMenuName(), dto.getPrice());
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }

    public List<MenuDTO> list() {
        List<MenuDTO> items = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbConn();
            String sql = "select category, menu_no, menu_name, price from menuList order by menu_no";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String category = cursor.getString(0);
                int menuNo = cursor.getInt(1);
                String menuName = cursor.getString(2);
                int price = cursor.getInt(3);

                items.add(new MenuDTO(category, menuNo, menuName, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) cursor.close();
        }
        return items;
    }

    public void update(MenuDTO dto) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = String.format("update menuList set category='%s', menu_name='%s', price=%d where menu_no=%d", dto.getCategory(), dto.getMenuName(), dto.getPrice(), dto.getMenuNo());
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }

    public void delete(int menuNo) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = "delete from menuList where menu_no=" + menuNo;
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }

    public void inquiry(String category) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = "select * from menuList where category=" + category;
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }

    public void search(String menuName) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = "select * from menuList where menu_name=" + menuName;
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }
}