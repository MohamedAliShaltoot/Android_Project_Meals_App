package com.example.mealsapp.ui.main.fragments.search_fragment.presenter;

import com.example.mealsapp.data.model.Meal;
import java.util.List;

public interface SearchView {

    void showResults(List<Meal> meals);

    void showEmpty();
}

