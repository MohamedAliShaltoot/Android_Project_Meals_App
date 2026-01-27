package com.example.mealsapp.ui.main.fragments.home_fragment;

import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.model.Category;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.ui.main.CategoryMealsActivity;
import com.example.mealsapp.ui.main.MealDetailsActivity;
import com.example.mealsapp.ui.main.adapters.CategoryAdapter;
import com.example.mealsapp.ui.main.fragments.search_fragment.SearchFragment;
import com.example.mealsapp.ui.main.fragments.home_fragment.presenter.HomeContract;
import com.example.mealsapp.ui.main.fragments.home_fragment.presenter.HomePresenterImp;
import com.example.mealsapp.ui.main.fragments.home_fragment.repo.HomeRepoImp;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {

    private EditText etSearch;
    private MaterialCardView cardRandomMeal;
    private ImageView imgRandomMeal;
    private TextView tvRandomMealName;
    private RecyclerView rvCategories;

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

        presenter = new HomePresenterImp(this, new HomeRepoImp());

        setupSearch();
        setupRecyclerView();
        setupClicks();

        presenter.getRandomMeal();
        presenter.getCategories();

        return view;
    }

    private void setupSearch() {
        etSearch.setFocusable(false);
        etSearch.setOnClickListener(v ->
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new SearchFragment())
                        .addToBackStack(null)
                        .commit()
        );
    }

    private void setupRecyclerView() {
        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        categoryAdapter = new CategoryAdapter(
                getContext(),
                categoryList,
                categoryName -> {
                    Intent intent = new Intent(getContext(), CategoryMealsActivity.class);
                    intent.putExtra("category", categoryName);
                    startActivity(intent);
                }
        );
        rvCategories.setAdapter(categoryAdapter);
    }

    private void setupClicks() {
        cardRandomMeal.setOnClickListener(v -> {
            if (randomMeal != null) {
                Intent intent = new Intent(getContext(), MealDetailsActivity.class);
                intent.putExtra("meal_id", randomMeal.getIdMeal());
                startActivity(intent);
            }
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
}
