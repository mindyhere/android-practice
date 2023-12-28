package com.example.cafemanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    Context context;
    SQLiteDatabase db;

    public MenuDAO(Context context) {
        this.context = context;
    }

    public SQLiteDatabase dbConn() {
        db = context.openOrCreateDatabase("cafe.db", Context.MODE_PRIVATE, null);
        String createCategoryTab = "CREATE TABLE if not exists categoryTab(category_id TEXT(2) PRIMARY KEY, category text NOT NULL)";
        String createMenuList = "CREATE TABLE if not exists menuList(category_id TEXT, menu_no integer primary key AUTOINCREMENT, menu_name text not null, menu_id TEXT AS (printf('%s%03d', category_id, menu_no)) stored, price integer default 0 not null, run integer not null check (run in (0,1)) default 1, foreign key (category_id) references categoryTab (category_id))";
        String createView = "create view if not exists menuView as select category, menu_id, menu_name, price, run from categoryTab c, menuList m where c.category_id=m.category_id";
        db.execSQL(createCategoryTab);
        db.execSQL(createMenuList);
        db.execSQL(createView);

//        String sql1 = "insert into categoryTab values ('cf', 'coffee')";
//        String sql2 = "insert into categoryTab values ('bv', 'beverage')";
//        String sql3 = "insert into categoryTab values ('bv', 'beverage')";
//        String sql4 = "insert into categoryTab values ('bv', 'beverage')";
//        db.execSQL(sql1);
//        db.execSQL(sql2);
//        db.execSQL(sql3);
//        db.execSQL(sql4);

        return db;
    }

//    public void editCategory(MenuDTO dto) {
//        SQLiteDatabase db = null;
//        try {
//            db = dbConn();
//            String sql = String.format("insert into categoryTab (category_id, category) values ('%s', '%s')", dto.getCategoryId(), dto.getCategory());
//            db.execSQL(sql);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (db != null) db.close();
//        }
//    }

    public void insert(MenuDTO dto) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = String.format("insert into menuList (category_id, menu_name, price, run) values ('%s', '%s', %d, %d)", dto.getCategoryId(), dto.getMenuName(), dto.getPrice(), dto.getRun());
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }

    public List<MenuDTO> list() {
        List<MenuDTO> menus = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbConn();
            String sql = "select category, menu_name, price, run from menuView order by category, menu_id";
            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                String category = cursor.getString(0);
                String menuName = cursor.getString(1);
                int price = cursor.getInt(2);
                int run = cursor.getInt(3);

                menus.add(new MenuDTO(category, menuName, price, run));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return menus;
    }

    public void update(MenuDTO dto) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = String.format("update menuList set menu_name='%s', price=%d, run=%d where menu_id=%s", dto.getMenuName(), dto.getPrice(), dto.getRun(), dto.getMenuId());
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }

    public void delete(String menuId) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = "delete from menuList where menu_id=" + menuId;
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }

}
