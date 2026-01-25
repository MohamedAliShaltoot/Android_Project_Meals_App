package com.example.mealsapp.ui.main.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealsapp.R;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.RetrofitClient;
import com.example.mealsapp.ui.main.adapters.MealsAdapter;
import com.example.mealsapp.utils.AppSnackbar;
import com.example.mealsapp.utils.SnackType;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    TextInputEditText etSearch;
    RecyclerView rvResults;
    ImageView imgPlaceholder, imgEmpty;
    LinearLayout layoutEmpty;

    MealsAdapter mealsAdapter;
    List<Meal> mealsList = new ArrayList<>();

    ChipGroup chipGroup;
    String selectedFilter = "";

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Views
        etSearch = view.findViewById(R.id.etSearchQuery);
        rvResults = view.findViewById(R.id.rvSearchResults);
        imgPlaceholder = view.findViewById(R.id.imgSearchPlaceholder);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);
        imgEmpty = view.findViewById(R.id.imgEmpty);
        chipGroup = view.findViewById(R.id.chipGroupFilter);
        TextInputLayout tilSearch = view.findViewById(R.id.tilSearch);

        // RecyclerView
        rvResults.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mealsAdapter = new MealsAdapter(getContext(), mealsList);
        rvResults.setAdapter(mealsAdapter);
        etSearch.setOnClickListener(v -> {
            if (selectedFilter.isEmpty()) {
                AppSnackbar.show(
                        v,
                        "Please select a filter first",
                        SnackType.INFO
                );
            }
        });


        // Chips
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {

            rvResults.setVisibility(View.GONE);
            imgPlaceholder.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);

            if (checkedId == View.NO_ID) {
                selectedFilter = "";
                tilSearch.setHint("Search value");
                disableSearchInput();
                return;
            }

            // Chip selected → enable input
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



        // Search action
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            performSearch();
            return true;
        });

        return view;
    }

    private void performSearch() {
        String query = etSearch.getText().toString().trim();
        if (query.isEmpty() || selectedFilter.isEmpty()) return;

        Call<MealsResponse> call = null;

        switch (selectedFilter) {
            case "Category":
                call = RetrofitClient.getApi().getMealsByCategory(query);
                break;
            case "Country":
                call = RetrofitClient.getApi().filterByArea(query);
                break;
            case "Ingredient":
                call = RetrofitClient.getApi().filterByIngredient(query);
                break;
        }

        if (call == null) return;

        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                mealsList.clear();

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getMeals() != null) {

                    mealsList.addAll(response.body().getMeals());

                    rvResults.setVisibility(View.VISIBLE);
                    imgPlaceholder.setVisibility(View.GONE);
                    layoutEmpty.setVisibility(View.GONE);
                } else {
                    showEmpty();
                }

                mealsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                showEmpty();
            }
        });
    }

    private void showEmpty() {
        rvResults.setVisibility(View.GONE);
        imgPlaceholder.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.VISIBLE);
    }
    private void enableSearchInput() {
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.setCursorVisible(true);
        etSearch.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    private void disableSearchInput() {
        etSearch.setText(""); // optional UX
        etSearch.setFocusable(false);
        etSearch.setFocusableInTouchMode(false);
        etSearch.setCursorVisible(false);
        etSearch.setInputType(InputType.TYPE_NULL);
    }

}
