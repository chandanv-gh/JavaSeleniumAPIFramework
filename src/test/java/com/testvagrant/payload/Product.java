package com.testvagrant.payload;

public class Product {
    private int id;
    private String name;
    private String price;
    private String brand;
    private Category category;

    // Getters and Setters
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Inner classes for Category and UserType
    public static class Category {
        private UserType usertype;
        private String category;

        // Getters and Setters
        public UserType getUsertype() {
            return usertype;
        }

        public void setUsertype(UserType usertype) {
            this.usertype = usertype;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    public static class UserType {
        private String usertype;

        // Getters and Setters
        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }
    }
}