package com.my.nmimsassignment;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.my.nmimsassignment.Models.Product;
import com.my.nmimsassignment.room.ProductRe;

@Database(entities = {ProductRe.class} ,  version = 3)

public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
