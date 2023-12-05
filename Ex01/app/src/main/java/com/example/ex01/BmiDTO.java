package com.example.ex01;

import java.io.Serializable;
//  Serializable 인터페이스를 구현 → Serialization 직렬화
//  객체에 저장된 데이터를 스트림에 쓰기(write) 위해 연속적인(serial) 데이터로 변환하는 것
//  객체 → 바이트 스트림

public class BmiDTO implements Serializable {
//                  Serializable 인터페이스 구현
//  DTO(Data Transfer Object): 데이터 전달객체
    private String name;
    private int age;
    private double height;
    private double weight;
    private double bmi;
    private String result;

    //  우클릭 → Generate(단축키 Alt+Insert) → getter & setter 추가
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

