package com.example.mealsapp.ui.main.fragments.home_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.model.Category;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.ui.main.adapters.CategoryAdapter;
import com.example.mealsapp.ui.main.fragments.home_fragment.presenter.HomeContract;
import com.example.mealsapp.ui.main.fragments.home_fragment.presenter.HomePresenterImp;
import com.example.mealsapp.data.home.HomeRepositoryImpl;
import com.example.mealsapp.utils.NetworkUtils;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {

    private EditText etSearch;
    private MaterialCardView cardRandomMeal;
    private ImageView imgRandomMeal;
    private TextView tvRandomMealName;
    private RecyclerView rvCategories;
    private LottieAnimationView lottieNoInternet;
    private CategoryAdapter categoryAdapter;
    private final List<Category> categoryList = new ArrayList<>();
    private Meal randomMeal;
    private HomeContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        etSearch = view.findViewById(R.id.etSearch);
        cardRandomMeal = view.findViewById(R.id.cardRandomMeal);
        imgRandomMeal = view.findViewById(R.id.imgRandomMeal);
        tvRandomMealName = view.findViewById(R.id.tvRandomMealName);
        rvCategories = view.findViewById(R.id.rvCategories);
        lottieNoInternet = view.findViewById(R.id.lottieNoInternet);
        lottieNoInternet.setOnClickListener(v -> {
            presenter.getRandomMeal();
            presenter.getCategories();
        });

        //presenter = new HomePresenterImp(this, new HomeRepositoryImpl());
        presenter = new HomePresenterImp(
                this,
                new HomeRepositoryImpl()
        );
        setupSearch();
        setupRecyclerView();
        setupClicks();
        if (!NetworkUtils.isInternetAvailable(requireContext())) {
            showNoInternet();
        } else {
            presenter.getRandomMeal();
            presenter.getCategories();
        }
        return view;
    }

    private void setupSearch() {
        etSearch.setFocusable(false);
        etSearch.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_homeFragment_to_searchFragment)
        );
    }

    private void setupRecyclerView() {
        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        categoryAdapter = new CategoryAdapter(
                requireContext(),
                categoryList,
                categoryName -> {
                    HomeFragmentDirections.ActionHomeFragmentToCategoryMealsFragment action =
                            HomeFragmentDirections
                                    .actionHomeFragmentToCategoryMealsFragment(categoryName);

                    NavHostFragment.findNavController(this)
                            .navigate(action);
                }
        );
        rvCategories.setAdapter(categoryAdapter);
    }

    private void setupClicks() {
        cardRandomMeal.setOnClickListener(v -> {
            if (randomMeal == null) return;

            HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                    HomeFragmentDirections
                            .actionHomeFragmentToMealDetailsFragment(randomMeal.getIdMeal());

            NavHostFragment.findNavController(this)
                    .navigate(action);
        });
    }


    @Override
    public void showCategories(List<Category> categories) {
        categoryList.clear();
        categoryList.addAll(categories);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRandomMeal(Meal meal) {
        randomMeal = meal;
        tvRandomMealName.setText(meal.getStrMeal());
        Glide.with(requireContext())
                .load(meal.getStrMealThumb())
                .into(imgRandomMeal);
    }

    @Override
    public void showError(String message) {
       // do it later
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }
    @Override
    public void showNoInternet() {
        lottieNoInternet.setVisibility(View.VISIBLE);
        cardRandomMeal.setVisibility(View.GONE);
        rvCategories.setVisibility(View.GONE);
    }

    @Override
    public void hideNoInternet() {
        lottieNoInternet.setVisibility(View.GONE);
        cardRandomMeal.setVisibility(View.VISIBLE);
        rvCategories.setVisibility(View.VISIBLE);
    }

}
