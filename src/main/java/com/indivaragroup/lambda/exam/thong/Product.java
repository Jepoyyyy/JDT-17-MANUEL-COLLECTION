package com.indivaragroup.lambda.exam.thong;

import java.text.DecimalFormat;
import java.util.function.Consumer;

public class Product {
    private String productName;
    private String category;
    private String size;
    private Double price;


    public Product(String productName, String category, String size, Double price) {
        this.productName = productName;
        this.category = category;
        this.size = size;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                '}';
    }

//    public Object toLowerCase() {
//
//    }
}