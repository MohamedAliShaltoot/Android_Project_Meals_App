package com.example.mealsapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
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

public class MealDetailsFragment extends Fragment {

    ImageView imgMeal;
    ImageButton btnFavorite;
    TextView tvName, tvCountry, tvSteps;
    RecyclerView rvIngredients;
    YouTubePlayerView youTubePlayerView;
    private String mealId;
    MealsDatabase db;
    Meal currentMeal;
    boolean isFavorite = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view = inflater.inflate(
                R.layout.activity_meal_details,
                container,
                false
        );

        MealDetailsFragmentArgs args =
                MealDetailsFragmentArgs.fromBundle(getArguments());

         mealId = args.getMealId();

        // Replaces getIntent()
//        Bundle args = getArguments();
//        String mealId = null;
//        if (args != null) {
//            mealId = args.getString("meal_id");
//        }

        imgMeal = view.findViewById(R.id.imgMeal);
        btnFavorite = view.findViewById(R.id.btnFavorite);
        tvName = view.findViewById(R.id.tvMealName);
        tvCountry = view.findViewById(R.id.tvCountry);
        tvSteps = view.findViewById(R.id.tvSteps);
        youTubePlayerView = view.findViewById(R.id.youtubePlayerView);

        getLifecycle().addObserver(youTubePlayerView);

        rvIngredients = view.findViewById(R.id.rvIngredients);
        rvIngredients.setLayoutManager(
                new LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );

        db = MealsDatabase.getInstance(requireContext());

        if (mealId != null) {
            loadMealDetails(mealId);
        }

        btnFavorite.setOnClickListener(v -> toggleFavorite());

        return view;
    }

    private void loadMealDetails(String id) {
        RetrofitClient.getApi()
                .getMealDetails(id)
                .enqueue(new Callback<MealsResponse>() {
                    @Override
                    public void onResponse(
                            Call<MealsResponse> call,
                            Response<MealsResponse> response
                    ) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getMeals() != null) {

                            currentMeal =
                                    response.body().getMeals().get(0);

                            tvName.setText(currentMeal.getStrMeal());
                            tvCountry.setText(
                                    currentMeal.getStrArea() == null
                                            ? "Unknown"
                                            : currentMeal.getStrArea()
                            );

                            tvSteps.setText(
                                    currentMeal.getStrInstructions() == null
                                            ? "No instructions available"
                                            : currentMeal.getStrInstructions()
                            );

                            Glide.with(requireContext())
                                    .load(currentMeal.getStrMealThumb())
                                    .into(imgMeal);

                            isFavorite = db.favoriteMealDao()
                                    .isFavorite(currentMeal.getIdMeal());

                            rvIngredients.setAdapter(
                                    new IngredientsAdapter(
                                            currentMeal.getIngredientsList()
                                    )
                            );

                            String youtubeUrl = currentMeal.getStrYoutube();
                            if (youtubeUrl != null && !youtubeUrl.isEmpty()) {

                                String videoId =
                                        youtubeUrl.substring(
                                                youtubeUrl.lastIndexOf("=") + 1
                                        );

                                youTubePlayerView
                                        .addYouTubePlayerListener(
                                                new AbstractYouTubePlayerListener() {
                                                    @Override
                                                    public void onReady(
                                                            @NonNull YouTubePlayer youTubePlayer
                                                    ) {
                                                        youTubePlayer
                                                                .cueVideo(videoId, 0);
                                                    }
                                                }
                                        );
                            }

                            updateFavoriteIcon();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<MealsResponse> call,
                            Throwable t
                    ) {
                    }
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
                    currentMeal.getStrMeal()
                            + " Removed from favorites",
                    SnackType.INFO
            );
        } else {
            db.favoriteMealDao().insert(fav);
            AppSnackbar.show(
                    btnFavorite,
                    currentMeal.getStrMeal()
                            + " Added to favorites",
                    SnackType.SUCCESS
            );
        }

        isFavorite = !isFavorite;
        updateFavoriteIcon();
    }

    private void updateFavoriteIcon() {
        btnFavorite.setImageResource(
                isFavorite
                        ? R.drawable.ic_heart_filled
                        : R.drawable.ic_heart_outline
        );
    }
}
