package com.example.myapplication;

import java.io.Serializable;

public class PersonDTO implements Serializable {
    private int num;
    private String name;

   @Override
   public String toString() {
      return "PersonDTO{" +
              "num=" + num +
              ", name='" + name + '\'' +
              '}';
   }

   public int getNum() {
      return num;
   }

   public void setNum(int num) {
      this.num = num;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public PersonDTO(int num, String name) {
      this.num = num;
      this.name = name;
   }
}
