package model;

import java.time.LocalDateTime;
import java.util.List;

public class Dishes {

    private int dishId;
    private String dishName;
    private String description;
    private double price;
    private int categoryId;
    private boolean availability;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private FoodCategories category;
    private List<Inventory> inventories;

    public Dishes(int dishId, String dishName, String description, double price, int categoryId, boolean availability, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.availability = availability;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Dishes(int dishId, String dishName, String description, double price, int categoryId, boolean availability, LocalDateTime createdAt, LocalDateTime updatedAt, FoodCategories category, List<Inventory> inventories) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.availability = availability;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
        this.inventories = inventories;
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

    public FoodCategories getCategory() {
        return category;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setCategory(FoodCategories category) {
        this.category = category;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }
}
