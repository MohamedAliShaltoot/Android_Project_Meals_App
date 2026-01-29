package com.example.mealsapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
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

public class CategoryMealsFragment extends Fragment {

    RecyclerView rvMeals;
    MealsAdapter mealsAdapter;
    List<Meal> mealsList = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view = inflater.inflate(
                R.layout.activity_category_meals,
                container,
                false
        );

        // Replaces getIntent()
        Bundle args = getArguments();
        String categoryName = null;
        if (args != null) {
            categoryName = args.getString("category");
        }

        rvMeals = view.findViewById(R.id.rvMeals);
        rvMeals.setLayoutManager(
                new GridLayoutManager(requireContext(), 2)
        );

       // mealsAdapter = new MealsAdapter(requireContext(), mealsList);
        mealsAdapter = new MealsAdapter(
                mealsList,
                mealId -> {

                    MealDetailsFragment fragment =
                            new MealDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("meal_id", mealId);
                    fragment.setArguments(bundle);

                    requireActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
        );

        rvMeals.setAdapter(mealsAdapter);

        if (categoryName != null) {
            loadMealsByCategory(categoryName);
        }

        return view;
    }

    private void loadMealsByCategory(String category) {

        RetrofitClient.getApi()
                .getMealsByCategory(category)
                .enqueue(new Callback<MealsResponse>() {
                    @Override
                    public void onResponse(
                            Call<MealsResponse> call,
                            Response<MealsResponse> response
                    ) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            mealsList.clear();
                            mealsList.addAll(response.body().getMeals());
                            mealsAdapter.notifyDataSetChanged();
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
}

