package com.example.mealsapp.ui.main.fragments.fav_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealsapp.R;
import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.example.mealsapp.data.database.localDatabase.MealsDatabase;
import com.example.mealsapp.ui.main.adapters.FavoritesAdapter;
import com.example.mealsapp.ui.main.fragments.fav_fragment.presenter.FavoritesPresenter;
import com.example.mealsapp.ui.main.fragments.fav_fragment.presenter.FavoritesPresenterImpl;
import com.example.mealsapp.ui.main.fragments.fav_fragment.presenter.FavoritesView;
import com.example.mealsapp.ui.main.fragments.fav_fragment.repo.FavoritesRepositoryImpl;
import com.example.mealsapp.utils.AppSnackbar;
import com.example.mealsapp.utils.SnackType;

import java.util.List;

public class Favoritessfragment extends Fragment implements FavoritesView {

    RecyclerView rvFavorites;
    FavoritesAdapter adapter;
    FavoritesPresenter presenter;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new FavoritesAdapter(meal ->
                presenter.removeFavorite(meal)
        );

        rvFavorites.setAdapter(adapter);

        presenter = new FavoritesPresenterImpl(
                this,
                new FavoritesRepositoryImpl(
                        MealsDatabase.getInstance(requireContext()).favoriteMealDao(),
                        getViewLifecycleOwner()
                )
        );

        presenter.loadFavorites();

        setupSwipe();

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
    private void setupSwipe() {

        ItemTouchHelper.SimpleCallback callback =
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.LEFT
                ) {

                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target
                    ) {
                        return false;
                    }

                    @Override
                    public void onSwiped(
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            int direction
                    ) {

                        int position = viewHolder.getBindingAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            adapter.removeBySwipe(position, viewHolder.itemView);
                        }
                    }
                };

        new ItemTouchHelper(callback).attachToRecyclerView(rvFavorites);
    }

}
