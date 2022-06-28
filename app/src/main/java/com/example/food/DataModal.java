package com.example.food;

public class DataModal {
    String name;
    String address;
    String rating;
    String time;
    String url;
    String description;
    String ID;
    DataModal(){

    }

    public DataModal(String name, String address, String rating, String time, String url, String description, String ID) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.time = time;
        this.url = url;
        this.description = description;
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
