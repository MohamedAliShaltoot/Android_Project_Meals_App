package com.example.mealsapp.ui.main;

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
import com.example.mealsapp.data.network.RetrofitClient;
import com.example.mealsapp.ui.main.adapters.MealsAdapter;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryMealsFragment extends Fragment {

    RecyclerView rvMeals;
    MealsAdapter mealsAdapter;
    List<Meal> mealsList = new ArrayList<>();
    private NavController navController;
    private String categoryName;
    private CompositeDisposable disposable = new CompositeDisposable();

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

         categoryName = args.getCategory();

        rvMeals = view.findViewById(R.id.rvMeals);
        rvMeals.setLayoutManager(
                new GridLayoutManager(requireContext(), 2)
        );

        mealsAdapter = new MealsAdapter(
                mealsList,
                mealId -> {
                    // SafeArgs Navigation
                    CategoryMealsFragmentDirections
                            .ActionCategoryMealsFragmentToMealDetailsFragment action =
                            CategoryMealsFragmentDirections
                                    .actionCategoryMealsFragmentToMealDetailsFragment(mealId);

                    navController.navigate(action);

                }
        );

        rvMeals.setAdapter(mealsAdapter);

        if (categoryName != null) {
            loadMealsByCategory(categoryName);
        }

        return view;
    }

private void loadMealsByCategory(String category) {
    disposable.add(
            RetrofitClient.getApi()
                    .getMealsByCategory(category)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response -> {
                                if (response.getMeals() != null) {
                                    mealsList.clear();
                                    mealsList.addAll(response.getMeals());
                                    mealsAdapter.notifyDataSetChanged();
                                }
                            },
                            Throwable::printStackTrace
                    )
    );
}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.clear();
    }

}

