package com.example.rudolph_king;

import org.json.JSONException;
import org.json.JSONObject;

public class Gift {
    private int id;
    private String productName;
    private int price;
    private String company;
    private String pictureUrl;

    public Gift(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt("id");
        this.productName = jsonObject.getString("productName");
        this.price = Integer.parseInt(jsonObject.getString("price"));
        this.company = jsonObject.getString("company");
        this.pictureUrl = jsonObject.getString("pictureUrl");

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
