package com.testvagrant.appGenericFunction;

import com.testvagrant.payload.Product;

import java.util.List;

public class APIResponse {
    private int responseCode;
    private String responseMessage; // Assuming the response message is in the JSON
    private List<Product> products;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}