package com.example.ex02;

public class PhonebookDTO {
    private String name;
    private String tel;

    public PhonebookDTO(String name, String tel) {
        this.name = name;
        this.tel = tel;
    } //Alt+Insult → 생성자

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    //Alt+Insult → getter&setter
}
