package com.example.mealsapp.ui.main.fragments;

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
import com.example.mealsapp.data.model.CategoriesResponse;
import com.example.mealsapp.data.model.Category;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.RetrofitClient;
import com.example.mealsapp.ui.main.CategoryMealsActivity;
import com.example.mealsapp.ui.main.MealDetailsActivity;
import com.example.mealsapp.ui.main.adapters.CategoryAdapter;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    EditText etSearch ;
    // Random meal views
    private MaterialCardView cardRandomMeal;
    private ImageView imgRandomMeal;
    private TextView tvRandomMealName;
    private Meal randomMeal;

    // Categories
    private RecyclerView rvCategories;
    private CategoryAdapter categoryAdapter;
    private final List<Category> categoryList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        cardRandomMeal = view.findViewById(R.id.cardRandomMeal);
        imgRandomMeal = view.findViewById(R.id.imgRandomMeal);
        tvRandomMealName = view.findViewById(R.id.tvRandomMealName);
        rvCategories = view.findViewById(R.id.rvCategories);
        etSearch = view.findViewById(R.id.etSearch);

        etSearch.setFocusable(false);
        etSearch.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new SearchFragment())
                    .addToBackStack(null)
                    .commit();
        });


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


        cardRandomMeal.setOnClickListener(v -> {
            if (randomMeal != null) {
                Intent intent = new Intent(getContext(), MealDetailsActivity.class);
                intent.putExtra("meal_id", randomMeal.getIdMeal());
                startActivity(intent);
            }
        });


        loadRandomMeal();
        loadCategories();

        return view;
    }


    private void loadCategories() {
        RetrofitClient.getApi()
                .getCategories()
                .enqueue(new Callback<CategoriesResponse>() {
                    @Override
                    public void onResponse(
                            Call<CategoriesResponse> call,
                            Response<CategoriesResponse> response
                    ) {
                        if (response.isSuccessful() && response.body() != null) {
                            categoryList.clear();
                            categoryList.addAll(response.body().getCategories());
                            categoryAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                    }
                });
    }

    private void loadRandomMeal() {
        RetrofitClient.getApi()
                .getRandomMeal()
                .enqueue(new Callback<MealsResponse>() {
                    @Override
                    public void onResponse(
                            Call<MealsResponse> call,
                            Response<MealsResponse> response
                    ) {
                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getMeals() != null) {

                            randomMeal = response.body().getMeals().get(0);

                            tvRandomMealName.setText(randomMeal.getStrMeal());

                            Glide.with(requireContext())
                                    .load(randomMeal.getStrMealThumb())
                                    .into(imgRandomMeal);
                        }
                    }

                    @Override
                    public void onFailure(Call<MealsResponse> call, Throwable t) {
                    }
                });
    }
}
