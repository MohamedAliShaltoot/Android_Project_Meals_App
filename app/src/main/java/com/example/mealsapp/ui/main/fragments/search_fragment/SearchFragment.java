package com.example.mealsapp.ui.main.fragments.search_fragment;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealsapp.R;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.ui.main.MealDetailsFragment;
import com.example.mealsapp.ui.main.adapters.MealsAdapter;
import com.example.mealsapp.ui.main.fragments.search_fragment.presenter.SearchPresenter;
import com.example.mealsapp.ui.main.fragments.search_fragment.presenter.SearchPresenterImp;
import com.example.mealsapp.ui.main.fragments.search_fragment.presenter.SearchView;
import com.example.mealsapp.ui.main.fragments.search_fragment.repo.SearchRepoImp;
import com.example.mealsapp.utils.AppSnackbar;
import com.example.mealsapp.utils.SnackType;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements SearchView {

    private TextInputEditText etSearch;
    private RecyclerView rvResults;
    private ImageView imgPlaceholder, imgEmpty;
    private LinearLayout layoutEmpty;
    private ChipGroup chipGroup;

    private MealsAdapter mealsAdapter;
    private final List<Meal> mealsList = new ArrayList<>();

    private String selectedFilter = "";

    private SearchPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = view.findViewById(R.id.etSearchQuery);
        rvResults = view.findViewById(R.id.rvSearchResults);
        imgPlaceholder = view.findViewById(R.id.imgSearchPlaceholder);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);
        imgEmpty = view.findViewById(R.id.imgEmpty);
        chipGroup = view.findViewById(R.id.chipGroupFilter);
        TextInputLayout tilSearch = view.findViewById(R.id.tilSearch);

        presenter = new SearchPresenterImp(this, new SearchRepoImp());

        rvResults.setLayoutManager(new GridLayoutManager(getContext(), 2));
      //  mealsAdapter = new MealsAdapter(getContext(), mealsList);
//        mealsAdapter = new MealsAdapter(
//                mealsList,
//                mealId -> {
//
//                    MealDetailsFragment fragment =
//                            new MealDetailsFragment();
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString("meal_id", mealId);
//                    fragment.setArguments(bundle);
//
//                    requireActivity()
//                            .getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.container, fragment)
//                            .addToBackStack(null)
//                            .commit();
//                }
//        );
        mealsAdapter = new MealsAdapter(
                mealsList,
                mealId -> {
                    SearchFragmentDirections.ActionSearchFragmentToMealDetailsFragment action =
                            SearchFragmentDirections
                                    .actionSearchFragmentToMealDetailsFragment(mealId);

                    NavHostFragment.findNavController(this)
                            .navigate(action);
                }
        );

        rvResults.setAdapter(mealsAdapter);

        etSearch.setOnClickListener(v -> {
            if (selectedFilter.isEmpty()) {
                AppSnackbar.show(v, "Please select a filter first", SnackType.INFO);
            }
        });

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {

            resetUI();

            if (checkedId == View.NO_ID) {
                selectedFilter = "";
                tilSearch.setHint("Search value");
                disableSearchInput();
                return;
            }

            enableSearchInput();

            if (checkedId == R.id.chipCountry) {
                selectedFilter = "Country";
                tilSearch.setHint("Search by country");
            } else if (checkedId == R.id.chipCategory) {
                selectedFilter = "Category";
                tilSearch.setHint("Search by category");
            } else if (checkedId == R.id.chipIngredient) {
                selectedFilter = "Ingredient";
                tilSearch.setHint("Search by ingredient");
            }
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            presenter.search(selectedFilter, etSearch.getText().toString().trim());
            return true;
        });

        return view;
    }

    @Override
    public void showResults(List<Meal> meals) {
        mealsList.clear();
        mealsList.addAll(meals);
        mealsAdapter.notifyDataSetChanged();

        rvResults.setVisibility(View.VISIBLE);
        imgPlaceholder.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        rvResults.setVisibility(View.GONE);
        imgPlaceholder.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.VISIBLE);
    }

    private void resetUI() {
        rvResults.setVisibility(View.GONE);
        imgPlaceholder.setVisibility(View.VISIBLE);
        layoutEmpty.setVisibility(View.GONE);
    }

    private void enableSearchInput() {
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.setCursorVisible(true);
        etSearch.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    private void disableSearchInput() {
        etSearch.setText("");
        etSearch.setFocusable(false);
        etSearch.setFocusableInTouchMode(false);
        etSearch.setCursorVisible(false);
        etSearch.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }
}
