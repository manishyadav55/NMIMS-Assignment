package com.my.nmimsassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class updateActivity extends AppCompatActivity {
    EditText id,name,description,brand,price,discountPercentage,rating,stock,category;
    ImageView thumbnail;
    Button btn_update;

    String text_name,txt_description,txt_brand,img_thumbnail;
    double txt_discountPercentage,txt_rating;
    int in_id, in_price;
    public static final String EXTRA_ID = "1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        id=findViewById(R.id.txt_id);
        name=findViewById(R.id.txt_title);
        description =findViewById(R.id.txt_description);
        brand=findViewById(R.id.txt_brand);
        price=findViewById(R.id.txt_price);
        discountPercentage=findViewById(R.id.txt_discountPercentage);
        rating=findViewById(R.id.txt_rating);
        thumbnail=findViewById(R.id.img_thumbnail);
        btn_update=findViewById(R.id.btn_update);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            in_id=extras.getInt("id");
            text_name = extras.getString("name");
            txt_description = extras.getString("description");
            txt_brand = extras.getString("brand");
            in_price = extras.getInt("price");
            txt_discountPercentage=extras.getDouble("discountPercentage");
            txt_rating=extras.getDouble("rating");
            img_thumbnail = extras.getString("thumbnail");

        }
        id.setText(String.valueOf(in_id));
        name.setText(text_name);
        description.setText(txt_description);
        brand.setText(txt_brand);
        price.setText(String.valueOf(in_price));
        discountPercentage.setText(String.valueOf(txt_discountPercentage));
        rating.setText(String.valueOf(txt_rating));
        Glide.with(this).load(img_thumbnail).into(thumbnail);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String IN_name=name.getText().toString();
                String IN_description=name.getText().toString();
                String IN_brand=brand.getText().toString();
                String IN_price=price.getText().toString();

                Intent i = new Intent(updateActivity.this,MainActivity.class);
                i.putExtra("title",IN_name);
                i.putExtra("description",IN_description);
                i.putExtra("brand",IN_brand);
                i.putExtra("price",IN_price);
                int id = getIntent().getIntExtra(EXTRA_ID, -1);
                if (id != -1) {
                    // in below line we are passing our id.
                    i.putExtra(EXTRA_ID, id);
                }

                // at last we are setting result as data.
                setResult(RESULT_OK, i);
                startActivity(i);
            }
        });
    }
}