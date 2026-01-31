package com.example.mealsapp.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.database.planner_dao.PlannedMeal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalenderViewHolder> {

    private List<PlannedMeal> list = new ArrayList<>();
    private final Consumer<PlannedMeal> onDelete;

    public CalendarAdapter(Consumer<PlannedMeal> onDelete) {
        this.onDelete = onDelete;
    }
    @Override
    public CalenderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calendar_meal, parent, false);
        return new CalenderViewHolder(v);
    }
    public void submitList(List<PlannedMeal> meals) {
        list = meals;
        notifyDataSetChanged();
    }

    class CalenderViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, date;
        ImageButton delete;

        CalenderViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.imgMeal);
            name = v.findViewById(R.id.tvName);
            date = v.findViewById(R.id.tvDate);
            delete = v.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public void onBindViewHolder(CalenderViewHolder h, int i) {
        PlannedMeal meal = list.get(i);

        h.name.setText(meal.mealName);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM", Locale.getDefault());
        h.date.setText(sdf.format(new Date(meal.date)));

        Glide.with(h.itemView).load(meal.mealThumb).into(h.img);

        h.delete.setOnClickListener(v -> onDelete.accept(meal));
    }

    @Override public int getItemCount() { return list.size(); }
}
