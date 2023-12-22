package com.example.cafemanagement;

import java.io.Serializable;

public class MenuDTO implements Serializable {
    private String category;
    private int menuNo;
    private String menuName;
    private int price;
    private int run;

    public MenuDTO(String category, int menuNo, String menuName, int price, int run) {
        this.category = category;
        this.menuNo = menuNo;
        this.menuName = menuName;
        this.price = price;
        this.run = run;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(int menuNo) {
        this.menuNo = menuNo;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "category='" + category + '\'' +
                ", menuNo=" + menuNo +
                ", menuName='" + menuName + '\'' +
                ", price=" + price +
                ", run=" + run +
                '}';
    }
}
