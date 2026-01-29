package com.example.mealsapp.ui.main.fragments.category_meals_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealsapp.R;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.ui.main.adapters.MealsAdapter;
import com.example.mealsapp.ui.main.fragments.category_meals_fragment.presenter.CategoryMealsContract;
import com.example.mealsapp.ui.main.fragments.category_meals_fragment.presenter.CategoryMealsPresenterImpl;
import com.example.mealsapp.ui.main.fragments.category_meals_fragment.repo.CategoryMealsRepoImpl;

import java.util.ArrayList;
import java.util.List;


public class CategoryMealsFragment extends Fragment
        implements CategoryMealsContract.View {

    private RecyclerView rvMeals;
    private MealsAdapter mealsAdapter;
    private final List<Meal> mealsList = new ArrayList<>();
    private NavController navController;
    private CategoryMealsContract.Presenter presenter;

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

        navController = NavHostFragment.findNavController(this);

        CategoryMealsFragmentArgs args =
                CategoryMealsFragmentArgs.fromBundle(getArguments());
        String categoryName = args.getCategory();

        rvMeals = view.findViewById(R.id.rvMeals);
        rvMeals.setLayoutManager(
                new GridLayoutManager(requireContext(), 2)
        );

        mealsAdapter = new MealsAdapter(
                mealsList,
                mealId -> {
                    CategoryMealsFragmentDirections
                            .ActionCategoryMealsFragmentToMealDetailsFragment action =
                            CategoryMealsFragmentDirections
                                    .actionCategoryMealsFragmentToMealDetailsFragment(mealId);

                    navController.navigate(action);
                }
        );

        rvMeals.setAdapter(mealsAdapter);

        presenter = new CategoryMealsPresenterImpl(
                this,
                new CategoryMealsRepoImpl()
        );

        if (categoryName != null) {
            presenter.loadMeals(categoryName);
        }

        return view;
    }

    @Override
    public void showMeals(List<Meal> meals) {
        mealsList.clear();
        mealsList.addAll(meals);
        mealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.clear();
    }
}


