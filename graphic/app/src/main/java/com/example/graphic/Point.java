package com.example.graphic;

public class Point {
    float x, y; //좌표값
    boolean isDraw; //그리기 상태(true or false)

    public Point(float x, float y, boolean isDraw) {
        this.x = x;
        this.y = y;
        this.isDraw = isDraw;
    }
}