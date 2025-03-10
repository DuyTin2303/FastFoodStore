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
    private double price;
    private String category;
    private boolean availability;
    private String imageUrl;
    private String description; // Field for dish description

    public FoodItem(int id, String name, String category, double price, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
}




