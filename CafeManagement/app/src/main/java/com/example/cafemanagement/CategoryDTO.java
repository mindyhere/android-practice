package com.example.cafemanagement;

public class CategoryDTO {
    private String categoryId;
    private String category;

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId='" + categoryId + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CategoryDTO(String categoryId, String category) {
        this.categoryId = categoryId;
        this.category = category;
    }
}
