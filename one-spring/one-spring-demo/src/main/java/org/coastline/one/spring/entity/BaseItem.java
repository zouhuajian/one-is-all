package org.coastline.one.spring.entity;

/**
 * product item
 * @author Jay.H.Zou
 * @date 2021/7/16
 */
public class BaseItem {

    private int id;

    private String name;

    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
