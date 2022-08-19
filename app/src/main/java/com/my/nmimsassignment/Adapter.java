package com.my.nmimsassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.nmimsassignment.Models.Product;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    Context context;
    List<Product> arrayList;
    public Adapter(Context context, List<Product> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Product product = arrayList.get(position);

        holder.list_name.setText(product.title);
        holder.txt_description.setText(product.description);
        holder.txt_brand.setText("Brand: "+product.brand);
        holder.txt_price.setText("Price: " + product.price);

        Glide.with(context)
                .load(product.thumbnail)
                .into(holder.img_thumbnail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=product.id;
                String name=product.title;
                String description= product.description;
                String brand= product.brand;
                int price= product.price;
                double discountPercentage= product.discountPercentage;
                double rating=product.rating;
                int stock=product.stock;
                String category=product.category;
                String thumbnail= product.thumbnail;

                Intent i = new Intent(context.getApplicationContext(),updateActivity.class);
                i.putExtra("id",id);
                i.putExtra("name",name);
                i.putExtra("description",description);
                i.putExtra("brand",brand);
                i.putExtra("price",price);
                i.putExtra("discountPercentage",discountPercentage);
                i.putExtra("rating",rating);
                i.putExtra("stock",stock);
                i.putExtra("category",category);
                i.putExtra("thumbnail",thumbnail);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView list_name, txt_brand, txt_price,txt_description;
        public ImageView img_thumbnail;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            txt_description= itemView.findViewById(R.id.txt_description);
            txt_brand= itemView.findViewById(R.id.txt_brand);
            txt_price = itemView.findViewById(R.id.txt_price);
            img_thumbnail = itemView.findViewById(R.id.img_thumbnail);
        }
    }
}
