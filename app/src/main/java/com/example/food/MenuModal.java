package com.example.food;

public class MenuModal {
    String name,discount,description,restro_id,price,url;

    public MenuModal(){

    }
    public MenuModal(String name, String discount, String description, String restro_id, String price, String url) {
        this.name = name;
        this.discount = discount;
        this.description = description;
        this.restro_id = restro_id;
        this.price = price;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRestro_id() {
        return restro_id;
    }

    public void setRestro_id(String restro_id) {
        this.restro_id = restro_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
