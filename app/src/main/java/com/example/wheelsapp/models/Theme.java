package com.example.wheelsapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "theme")
public class Theme {

    @PrimaryKey(autoGenerate = true)
    long id;
    int color;


    public Theme(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
