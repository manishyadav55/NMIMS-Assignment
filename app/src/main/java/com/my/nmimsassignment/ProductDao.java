package com.my.nmimsassignment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.my.nmimsassignment.Models.Product;
import com.my.nmimsassignment.room.ProductRe;

import java.util.List;

    @Dao
    public interface ProductDao {

        @Insert
        void insert(ProductRe productRe);

        @Update
        void update(ProductRe productRe);

        @Query("SELECT * FROM ProductRe")
        List<ProductRe> getAll();



    }
