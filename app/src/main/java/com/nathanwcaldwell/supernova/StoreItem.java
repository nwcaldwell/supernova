package com.nathanwcaldwell.supernova;

/**
 * Created by nathan on 2/19/15.
 */
public class StoreItem {

    private String name;
    private int upgradeNumber;
    private int price;
    private boolean purchased;

    public StoreItem() {

    }

    public StoreItem(String name, int upgradeNumber, int price, boolean purchased) {
        this.name = name;
        this.upgradeNumber = upgradeNumber;
        this.price = price;
        this.purchased = purchased;
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

    public boolean getPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
