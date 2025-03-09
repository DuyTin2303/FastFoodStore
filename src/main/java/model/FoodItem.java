/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author a
 */
public class FoodItem {

    private int id;
    private String name;
    private String category;
    private double price;
    private String review;
    private String imageUrl; // New field for image URL

    public FoodItem(int id, String name, String category, double price, String review, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.review = review;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getReview() {
        return review;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
}