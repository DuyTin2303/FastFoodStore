package model;

import java.time.LocalDateTime;
import java.util.List;

public class FoodCategories {

    private int categoryId;
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Dishes> dishes;

    public FoodCategories(int categoryId, String categoryName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public FoodCategories(int categoryId, String categoryName, LocalDateTime createdAt, LocalDateTime updatedAt, List<Dishes> dishes) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dishes = dishes;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Dishes> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dishes> dishes) {
        this.dishes = dishes;
    }
}
