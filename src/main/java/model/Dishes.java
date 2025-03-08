package model;

import java.time.LocalDateTime;

public class Dishes {

    private int dishId;
    private String dishName;
    private String description;
    private double price;
    private int categoryId;
    private boolean availability;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imageUrl;

    private FoodCategories category;

    public Dishes(int dishId, String dishName, String description, double price, int categoryId, boolean availability, LocalDateTime createdAt, LocalDateTime updatedAt, String imageUrl) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.availability = availability;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageUrl = imageUrl;
    }

    public Dishes(int dishId, String dishName, String description, double price, int categoryId, boolean availability, LocalDateTime createdAt, LocalDateTime updatedAt, String imageUrl, FoodCategories category) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.availability = availability;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public int getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public boolean isAvailability() {
        return availability;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public FoodCategories getCategory() {
        return category;
    }


    public void setCategory(FoodCategories category) {
        this.category = category;
    }
}
