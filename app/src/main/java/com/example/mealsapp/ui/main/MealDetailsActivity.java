package com.example.mealsapp.ui.details;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetailsActivity extends AppCompatActivity {

    ImageView imgMeal;
    TextView tvName, tvCountry, tvSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        String mealId = getIntent().getStringExtra("meal_id");

        imgMeal = findViewById(R.id.imgMeal);
        tvName = findViewById(R.id.tvMealName);
        tvCountry = findViewById(R.id.tvCountry);
        tvSteps = findViewById(R.id.tvSteps);

        loadMealDetails(mealId);
    }

    private void loadMealDetails(String id) {

        RetrofitClient.getApi()
                .getMealDetails(id)
                .enqueue(new Callback<MealsResponse>() {
                    @Override
                    public void onResponse(Call<MealsResponse> call,
                                           Response<MealsResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            Meal meal = response.body().getMeals().get(0);

                            tvName.setText(meal.getStrMeal());
                            tvCountry.setText(
                                    meal.getStrArea() == null ? "Unknown" : meal.getStrArea()
                            );

                            tvSteps.setText(
                                    meal.getStrInstructions() == null ? "No instructions available" : meal.getStrInstructions()
                            );


                            Glide.with(MealDetailsActivity.this)
                                    .load(meal.getStrMealThumb())
                                    .into(imgMeal);
                        }
                    }

                    @Override
                    public void onFailure(Call<MealsResponse> call, Throwable t) {
                    }
                });
    }
}

