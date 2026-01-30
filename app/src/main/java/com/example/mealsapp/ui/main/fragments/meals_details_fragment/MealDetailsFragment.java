package com.example.mealsapp.ui.main.fragments.meals_details_fragment;

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
import com.example.mealsapp.data.database.localDatabase.MealsDatabase;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.ui.main.adapters.IngredientsAdapter;
import com.example.mealsapp.ui.main.fragments.meals_details_fragment.presenter.MealDetailsContract;
import com.example.mealsapp.ui.main.fragments.meals_details_fragment.presenter.MealDetailsPresenterImpl;
import com.example.mealsapp.ui.main.fragments.meals_details_fragment.repo.MealDetailsRepositoryImpl;
import com.example.mealsapp.utils.AppSnackbar;
import com.example.mealsapp.utils.SnackType;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class MealDetailsFragment extends Fragment
        implements MealDetailsContract.View {

    private ImageView imgMeal;
    private ImageButton btnFavorite;
    private TextView tvName, tvCountry, tvSteps;
    private RecyclerView rvIngredients;
    private YouTubePlayerView youtubePlayerView;

    private MealDetailsContract.Presenter presenter;

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

        imgMeal = view.findViewById(R.id.imgMeal);
        btnFavorite = view.findViewById(R.id.btnFavorite);
        tvName = view.findViewById(R.id.tvMealName);
        tvCountry = view.findViewById(R.id.tvCountry);
        tvSteps = view.findViewById(R.id.tvSteps);
        youtubePlayerView = view.findViewById(R.id.youtubePlayerView);

        getLifecycle().addObserver(youtubePlayerView);

        rvIngredients = view.findViewById(R.id.rvIngredients);
        rvIngredients.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL, false)
        );

        presenter = new MealDetailsPresenterImpl(
                this,
                new MealDetailsRepositoryImpl(
                        MealsDatabase.getInstance(requireContext())
                                .favoriteMealDao()

                ),requireContext()
        );
        btnFavorite.setOnClickListener(v -> presenter.toggleFavorite());

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MealDetailsFragmentArgs args =
                MealDetailsFragmentArgs.fromBundle(getArguments());

        presenter.loadMeal(args.getMealId());
    }

    @Override
    public void showMeal(Meal meal) {
        tvName.setText(meal.getStrMeal());
        tvCountry.setText(
                meal.getStrArea() == null ? "Unknown" : meal.getStrArea()
        );
        tvSteps.setText(
                meal.getStrInstructions() == null
                        ? "No instructions available"
                        : meal.getStrInstructions()
        );

        Glide.with(this)
                .load(meal.getStrMealThumb())
                .into(imgMeal);

        rvIngredients.setAdapter(
                new IngredientsAdapter(meal.getIngredientsList())
        );

        setupYoutube(meal.getStrYoutube());
    }

    @Override
    public void showFavoriteState(boolean isFavorite) {
        btnFavorite.setImageResource(
                isFavorite
                        ? R.drawable.ic_heart_filled
                        : R.drawable.ic_heart_outline
        );
    }
    @Override
    public void showMessage(String message, SnackType type) {
        View root = requireView().findViewById(R.id.rootLayout);
        AppSnackbar.show(root, message, type);
    }

    @Override
    public void showError(String message) {
        View root = requireView().findViewById(R.id.rootLayout);
        AppSnackbar.show(root, message, SnackType.ERROR);
    }

@Override
public void showGuestView() {
    btnFavorite.setEnabled(false);
    View root = requireView().findViewById(R.id.rootLayout);
    AppSnackbar.show(root, "Login to use favorites", SnackType.INFO);
}


    private void setupYoutube(String url) {
        if (url == null || url.isEmpty()) return;

        String videoId = url.substring(url.lastIndexOf("=") + 1);

        youtubePlayerView.addYouTubePlayerListener(
                new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer player) {
                        player.cueVideo(videoId, 0);
                    }
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.clear();
    }
}
