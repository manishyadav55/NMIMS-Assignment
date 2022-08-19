package com.my.nmimsassignment.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class ProductRe implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "price")
    int price;

    @ColumnInfo(name = "discountPercentage")
    double discountPercentage;

    @ColumnInfo(name = "rating")
    double rating;

    @ColumnInfo(name = "stock")
    int stock;

    @ColumnInfo(name = "brand")
    String brand;

    @ColumnInfo(name = "category")
    String category;

    @ColumnInfo(name = "thumbnail")
    String thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
