package com.example.mealsapp.ui.main.adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.ui.main.MealDetailsFragment;

import org.jspecify.annotations.NonNull;

import java.util.List;
public class MealsAdapter
        extends RecyclerView.Adapter<MealsAdapter.MealViewHolder> {

public interface OnMealClickListener {
    void onMealClick(String mealId);
}

    private List<Meal> meals;
    private OnMealClickListener listener;

    public MealsAdapter(
            List<Meal> meals,
            OnMealClickListener listener
    ) {
        this.meals = meals;
        this.listener = listener;
    }

@NonNull
@Override
public MealViewHolder onCreateViewHolder(
        @NonNull ViewGroup parent,
        int viewType
) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_meal, parent, false);
    return new MealViewHolder(view);
}
@Override
public void onBindViewHolder(
        @NonNull MealViewHolder holder,
        int position
) {
    Meal meal = meals.get(position);

    holder.tvMealName.setText(meal.getStrMeal());

    holder.itemView.setOnClickListener(v ->
            listener.onMealClick(meal.getIdMeal())
    );

    Glide.with(holder.itemView)
            .load(meal.getStrMealThumb())
            .into(holder.imgMeal);
}


    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMeal;
        TextView tvMealName;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            tvMealName = itemView.findViewById(R.id.tvMealName);
        }
    }
}

