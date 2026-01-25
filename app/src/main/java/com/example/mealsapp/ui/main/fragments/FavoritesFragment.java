package com.example.mealsapp.ui.main.fragments;

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
import com.example.mealsapp.data.database.localDatabase.MealsDatabase;
import com.example.mealsapp.ui.main.adapters.FavoritesAdapter;

public class FavoritesFragment extends Fragment {

    RecyclerView rvFavorites;
    FavoritesAdapter adapter;
    MealsDatabase db;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        db = MealsDatabase.getInstance(requireContext());

        adapter = new FavoritesAdapter(meal ->
                db.favoriteMealDao().delete(meal)
        );

        rvFavorites.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback callback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

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


        db.favoriteMealDao()
                .getAllFavorites()
                .observe(getViewLifecycleOwner(), favorites ->
                        adapter.submitList(favorites)
                );

        return view;
    }
}

