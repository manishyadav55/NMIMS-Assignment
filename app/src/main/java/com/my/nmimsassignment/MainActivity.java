package com.my.nmimsassignment;

import static android.nfc.NfcAdapter.EXTRA_ID;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.my.nmimsassignment.Models.Product;
import com.my.nmimsassignment.room.ProductRe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String JSON_DATA = "https://dummyjson.com/products";
    List<Product> products = new ArrayList<>();
    private RecyclerView recyclerview;
    private ArrayList<Product> arrayList;
    private Adapter adapter;
    private ProgressBar pb;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.pb);
        pb.setVisibility(View.GONE);

        recyclerview = findViewById(R.id.recyclerview);
        arrayList = new ArrayList<Product>();

        adapter = new Adapter(this, arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setAdapter(adapter);

        isConnected(this);

    }

    public boolean isConnected(Context context){
        ConnectivityManager
                cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting() && arrayList != null) {
            fetchfromServer();
        }else {
            fetchfromRoom();
        }
        return false;
    }

    private void fetchfromRoom() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                List<ProductRe> productsList = DatabaseClient.getInstance(MainActivity.this).getAppDatabase().productDao().getAll();
                arrayList.clear();
                for (ProductRe products : productsList) {
                    Product repo = new Product(
                            products.getId(),
                            products.getTitle(),
                            products.getDescription(),
                            products.getCategory(),
                            products.getThumbnail(),
                            products.getPrice(),
                            products.getDiscountPercentage(),
                            products.getRating(),
                            products.getStock(),
                            products.getBrand());
                    arrayList.add(repo);
                }
                // refreshing recycler view
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();

                    }
                });
            }
        });
        thread.start();
    }

    private void fetchfromServer() {
        pb.setVisibility(View.VISIBLE);

        String url="https://dummyjson.com/products";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("products");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                int id= jsonObject.getInt("id");
                                String title=jsonObject.getString("title");
                                String description=jsonObject.getString("description");
                                String thumbnail=jsonObject.getString("thumbnail");
                                int price=jsonObject.getInt("price");
                                double discountPercentage=jsonObject.getDouble("discountPercentage");
                                double rating=jsonObject.getDouble("rating");
                                int stock= jsonObject.getInt("stock");
                                String brand=jsonObject.getString("brand");
                                String category=jsonObject.getString("category");
                                arrayList.add(new Product(id,title,description, category, thumbnail,price,discountPercentage,rating,stock,brand));
                                Log.e("TAG", "res " + jsonObject.toString());

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        pb.setVisibility(View.GONE);
                        saveTask();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.setVisibility(View.GONE);
                Log.e("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);


    }
    private void saveTask() {

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a ProductRe
                for (int i = 0; i < products.size(); i++) {
                    ProductRe recipe=new ProductRe();
                    recipe.setId(products.get(i).getId());
                    recipe.setTitle(products.get(i).getTitle());
                    recipe.setDescription(products.get(i).getDescription());
                    recipe.setThumbnail(products.get(i).getThumbnail());
                    recipe.setPrice(products.get(i).getPrice());
                    recipe.setDiscountPercentage(products.get(i).getDiscountPercentage());
                    recipe.setRating(products.get(i).getRating());
                    recipe.setStock(products.get(i).getStock());
                    recipe.setBrand(products.get(i).getBrand());
                    recipe.setCategory(products.get(i).getCategory());

                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().productDao().insert(recipe);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }
        SaveTask st = new SaveTask();
        st.execute();
    }

}