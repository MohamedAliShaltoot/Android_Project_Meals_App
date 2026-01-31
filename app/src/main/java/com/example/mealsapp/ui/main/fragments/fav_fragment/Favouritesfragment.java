package com.example.mealsapp.ui.main.fragments.fav_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealsapp.R;
import com.example.mealsapp.data.database.dao.FavoriteMeal;
import com.example.mealsapp.data.database.MealsDatabase;
import com.example.mealsapp.data.favorites.FavoritesLocalDataSource;
import com.example.mealsapp.data.favorites.FavoritesRemoteDataSource;
import com.example.mealsapp.ui.main.adapters.FavoritesAdapter;
import com.example.mealsapp.ui.main.fragments.fav_fragment.presenter.FavoritesPresenter;
import com.example.mealsapp.ui.main.fragments.fav_fragment.presenter.FavoritesPresenterImpl;
import com.example.mealsapp.ui.main.fragments.fav_fragment.presenter.FavoritesView;
import com.example.mealsapp.data.favorites.FavoritesRepositoryImpl;
import com.example.mealsapp.utils.AppSnackbar;
import com.example.mealsapp.utils.SnackType;
import com.example.mealsapp.utils.UserSession;

import java.util.List;

public class Favouritesfragment extends Fragment implements FavoritesView {

    RecyclerView rvFavorites;
    FavoritesAdapter adapter;
    FavoritesPresenter presenter;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        if (UserSession.isGuest(requireContext())) {
            AppSnackbar.show(
                    container,
                    "Login to view favorites",
                    SnackType.INFO
            );
            return inflater.inflate(R.layout.login_to_see_fav, container, false);
        }
        rvFavorites = view.findViewById(R.id.rvFavorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new FavoritesAdapter(meal ->
                presenter.removeFavorite(meal)
        );

        rvFavorites.setAdapter(adapter);
        presenter = new FavoritesPresenterImpl(
                this,
                new FavoritesRepositoryImpl(
                        new FavoritesLocalDataSource(
                                MealsDatabase.getInstance(requireContext()).favoriteMealDao()
                        ),
                        new FavoritesRemoteDataSource( )
                )
        );
        presenter.loadFavorites();
        return view;
    }

    @Override
    public void showFavorites(List<FavoriteMeal> meals) {
        adapter.submitList(meals);
    }

    @Override
    public void showRemoveMessage(String mealName) {
        AppSnackbar.show(
                rvFavorites,
                mealName + " Removed from favorites",
                SnackType.INFO
        );
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter instanceof FavoritesPresenterImpl) {
            ((FavoritesPresenterImpl) presenter).clear();
        }
    }

}
