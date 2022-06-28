package com.example.food;

public class OrderModal implements java.io.Serializable{
    String name,url;
    int quant,totalPrice;
    public OrderModal(){

    }
    public OrderModal(String name, String url, int quant, int totalPrice) {
        this.name = name;
        this.url = url;
        this.quant = quant;
        this.totalPrice = totalPrice;
    }
    public String toString(){
        return (name+" "+url+" "+quant+" "+totalPrice);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
