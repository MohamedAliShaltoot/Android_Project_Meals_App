package com.example.mealsapp.ui.main;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealsapp.R;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.RetrofitClient;
import com.example.mealsapp.ui.main.adapters.MealsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryMealsActivity extends AppCompatActivity {

    RecyclerView rvMeals;
    MealsAdapter mealsAdapter;
    List<Meal> mealsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_meals);

        String categoryName = getIntent().getStringExtra("category");

        rvMeals = findViewById(R.id.rvMeals);
        rvMeals.setLayoutManager(new GridLayoutManager(this, 2));

        mealsAdapter = new MealsAdapter(this, mealsList);
        rvMeals.setAdapter(mealsAdapter);

        loadMealsByCategory(categoryName);
    }

    private void loadMealsByCategory(String category) {

        RetrofitClient.getApi()
                .getMealsByCategory(category)
                .enqueue(new Callback<MealsResponse>() {
                    @Override
                    public void onResponse(Call<MealsResponse> call,
                                           Response<MealsResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            mealsList.clear();
                            mealsList.addAll(response.body().getMeals());
                            mealsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<MealsResponse> call, Throwable t) {
                    }
                });
    }
}

