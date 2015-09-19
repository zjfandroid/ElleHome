package com.elle.pojo;

import java.io.Serializable;

/**
 * Created by jason on 15/8/24.
 */
public class LightColor implements Serializable{

    private String name;
    private int color;
    private int light;

    public LightColor(String name, int color, int light) {
        this.name = name;
        this.color = color;
        this.light = light;
    }


    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
