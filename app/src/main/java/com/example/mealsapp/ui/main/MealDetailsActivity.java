package com.example.mealsapp.ui.main;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.example.mealsapp.data.database.localDatabase.MealsDatabase;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.RetrofitClient;
import com.example.mealsapp.ui.main.adapters.IngredientsAdapter;
import com.example.mealsapp.utils.AppSnackbar;
import com.example.mealsapp.utils.SnackType;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


import org.jspecify.annotations.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetailsActivity extends AppCompatActivity {

    ImageView imgMeal;
    ImageButton btnFavorite;
    TextView tvName, tvCountry, tvSteps;
    RecyclerView rvIngredients;
    YouTubePlayerView youTubePlayerView;

    MealsDatabase db;
    Meal currentMeal;
    boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        String mealId = getIntent().getStringExtra("meal_id");

        imgMeal = findViewById(R.id.imgMeal);
        btnFavorite = findViewById(R.id.btnFavorite);
        tvName = findViewById(R.id.tvMealName);
        tvCountry = findViewById(R.id.tvCountry);
        tvSteps = findViewById(R.id.tvSteps);
        youTubePlayerView = findViewById(R.id.youtubePlayerView);
        getLifecycle().addObserver(youTubePlayerView);

        rvIngredients = findViewById(R.id.rvIngredients);
        rvIngredients.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        db = MealsDatabase.getInstance(this);

        loadMealDetails(mealId);

        btnFavorite.setOnClickListener(v -> toggleFavorite());
    }

    private void loadMealDetails(String id) {
        RetrofitClient.getApi()
                .getMealDetails(id)
                .enqueue(new Callback<MealsResponse>() {
                    @Override
                    public void onResponse(Call<MealsResponse> call,
                                           Response<MealsResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getMeals() != null) {

                            currentMeal = response.body().getMeals().get(0);

                            tvName.setText(currentMeal.getStrMeal());
                            tvCountry.setText(
                                    currentMeal.getStrArea() == null ? "Unknown" : currentMeal.getStrArea()
                            );
                            tvSteps.setText(
                                    currentMeal.getStrInstructions() == null
                                            ? "No instructions available"
                                            : currentMeal.getStrInstructions()
                            );

                            Glide.with(MealDetailsActivity.this)
                                    .load(currentMeal.getStrMealThumb())
                                    .into(imgMeal);

                            isFavorite = db.favoriteMealDao()
                                    .isFavorite(currentMeal.getIdMeal());
                            IngredientsAdapter adapter =
                                    new IngredientsAdapter(currentMeal.getIngredientsList());

                            rvIngredients.setAdapter(adapter);
                            String youtubeUrl = currentMeal.getStrYoutube();

                            if (youtubeUrl != null && !youtubeUrl.isEmpty()) {
                                String videoId = youtubeUrl.substring(youtubeUrl.lastIndexOf("=") + 1);

                                youTubePlayerView.addYouTubePlayerListener(
                                        new AbstractYouTubePlayerListener() {
                                            @Override
                                            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                                youTubePlayer.cueVideo(videoId, 0);
                                            }
                                        }
                                );
                            }

                            updateFavoriteIcon();
                        }
                    }

                    @Override
                    public void onFailure(Call<MealsResponse> call, Throwable t) {}
                });
    }

    private void toggleFavorite() {
        if (currentMeal == null) return;

        FavoriteMeal fav = new FavoriteMeal(
                currentMeal.getIdMeal(),
                currentMeal.getStrMeal(),
                currentMeal.getStrMealThumb()
        );

        if (isFavorite) {
            db.favoriteMealDao().delete(fav);
            AppSnackbar.show(
                    btnFavorite,
                    currentMeal.getStrMeal() + " Removed from favorites",
                    SnackType.INFO
            );

        } else {
            db.favoriteMealDao().insert(fav);
            AppSnackbar.show(
                    btnFavorite,
                    currentMeal.getStrMeal() + " Added to favorites",
                    SnackType.SUCCESS
            );


        }

        isFavorite = !isFavorite;
        updateFavoriteIcon();
    }


    private void updateFavoriteIcon() {
        btnFavorite.setImageResource(
                isFavorite ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline
        );


    }
}
