package com.example.mealsapp.ui.main.fragments.search_fragment.presenter;

public interface SearchPresenter {

    void search(String filter, String query);

    void onDestroy();
}

