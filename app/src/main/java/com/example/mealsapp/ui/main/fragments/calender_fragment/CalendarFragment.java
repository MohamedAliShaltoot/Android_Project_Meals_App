package com.example.mealsapp.ui.main.fragments.calender_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealsapp.R;
import com.example.mealsapp.data.calender.CalendarLocalDataSource;
import com.example.mealsapp.data.calender.CalendarSyncManager;
import com.example.mealsapp.data.calender.FirestoreCalendarRepository;
import com.example.mealsapp.data.database.MealsDatabase;
import com.example.mealsapp.data.database.planner_dao.PlannedMeal;
import com.example.mealsapp.ui.main.adapters.CalendarAdapter;

import java.util.List;

public class CalendarFragment extends Fragment implements CalendarContract.View {

    private CalendarPresenterImpl presenter;
    private CalendarAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        RecyclerView rv = view.findViewById(R.id.rvCalendar);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CalendarAdapter(meal -> presenter.deleteMeal(meal));
        rv.setAdapter(adapter);

        CalendarLocalDataSource local = new CalendarLocalDataSource(
                MealsDatabase.getInstance(requireContext()).plannedMealDao()
        );

        FirestoreCalendarRepository remote = new FirestoreCalendarRepository();

        CalendarSyncManager syncManager =
                new CalendarSyncManager(local, remote);

        presenter = new CalendarPresenterImpl(
                this,
                MealsDatabase.getInstance(requireContext()).plannedMealDao(),
                syncManager
        );

        presenter.loadMeals();

        return view;
    }

    @Override
    public void showMeals(List<PlannedMeal> meals) {
        adapter.submitList(meals);
    }

    @Override
    public void showEmpty() {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.clear();
    }
}