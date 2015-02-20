package com.nathanwcaldwell.supernova;

/**
 * Created by nathan on 2/19/15.
 */
public class StoreItem {

    private String name;
    private int upgradeNumber;
    private int price;

    public StoreItem() {

    }

    public StoreItem(String name, int upgradeNumber, int price) {
        this.name = name;
        this.upgradeNumber = upgradeNumber;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpgradeNumber() {
        return upgradeNumber;
    }

    public void setUpgradeNumber(int upgradeNumber) {
        this.upgradeNumber = upgradeNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
