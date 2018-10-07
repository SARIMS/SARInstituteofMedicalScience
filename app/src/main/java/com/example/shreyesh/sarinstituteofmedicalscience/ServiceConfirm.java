package com.example.shreyesh.sarinstituteofmedicalscience;


public class ServiceConfirm {

    private String price, itemName;

    public ServiceConfirm(String price, String itemName) {
        this.price = price;
        this.itemName = itemName;
    }

    public ServiceConfirm() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
