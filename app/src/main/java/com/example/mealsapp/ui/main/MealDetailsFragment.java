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
import com.example.mealsapp.data.network.RetrofitClient;
import com.example.mealsapp.ui.main.adapters.IngredientsAdapter;
import com.example.mealsapp.utils.AppSnackbar;
import com.example.mealsapp.utils.SnackType;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import org.jspecify.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
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
    private CompositeDisposable disposable = new CompositeDisposable();

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
        disposable.add(
                RetrofitClient.getApi()
                        .getMealDetails(id)
                        .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                        .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    if (response.getMeals() == null || response.getMeals().isEmpty()) return;

                                    currentMeal = response.getMeals().get(0);

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


                                    disposable.add(
                                            db.favoriteMealDao()
                                                    .isFavorite(currentMeal.getIdMeal())
                                                    .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                                                    .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                                                    .subscribe(
                                                            result -> {
                                                                isFavorite = result;
                                                                updateFavoriteIcon();
                                                            },
                                                            Throwable::printStackTrace
                                                    )
                                    );

                                    rvIngredients.setAdapter(
                                            new IngredientsAdapter(
                                                    currentMeal.getIngredientsList()
                                            )
                                    );

                                    setupYoutube(currentMeal.getStrYoutube());
                                },
                                Throwable::printStackTrace
                        )
        );
    }
    private void setupYoutube(String youtubeUrl) {
        if (youtubeUrl == null || youtubeUrl.isEmpty()) return;

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


    private void toggleFavorite() {
        if (currentMeal == null) return;

        FavoriteMeal fav = new FavoriteMeal(
                currentMeal.getIdMeal(),
                currentMeal.getStrMeal(),
                currentMeal.getStrMealThumb()
        );

        if (isFavorite) {
            disposable.add(
                    db.favoriteMealDao()
                            .delete(fav)
                            .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                            .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> {
                                        isFavorite = false;
                                        updateFavoriteIcon();
                                        AppSnackbar.show(
                                                btnFavorite,
                                                currentMeal.getStrMeal() + " Removed from favorites",
                                                SnackType.INFO
                                        );
                                    },
                                    Throwable::printStackTrace
                            )
            );
        } else {
            disposable.add(
                    db.favoriteMealDao()
                            .insert(fav)
                            .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                            .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> {
                                        isFavorite = true;
                                        updateFavoriteIcon();
                                        AppSnackbar.show(
                                                btnFavorite,
                                                currentMeal.getStrMeal() + " Added to favorites",
                                                SnackType.SUCCESS
                                        );
                                    },
                                    Throwable::printStackTrace
                            )
            );
        }
    }


    private void updateFavoriteIcon() {
        btnFavorite.setImageResource(
                isFavorite
                        ? R.drawable.ic_heart_filled
                        : R.drawable.ic_heart_outline
        );
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.clear();
    }

}
