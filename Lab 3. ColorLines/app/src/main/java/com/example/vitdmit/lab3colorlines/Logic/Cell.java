package com.example.vitdmit.lab3colorlines.Logic;

import android.graphics.Color;

public class Cell {
    private int color;
    private boolean used;

    public Cell(int color) {
        this.color = color;
        used = true;
    }

    public Cell() {
        used = false;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
