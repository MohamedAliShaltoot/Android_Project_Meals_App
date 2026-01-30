package com.example.mealsapp.ui.main.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.example.mealsapp.utils.AppSnackbar;
import com.example.mealsapp.utils.SnackType;
import java.util.ArrayList;
import java.util.List;
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<FavoriteMeal> list = new ArrayList<>();
    private final OnRemoveClick listener;

    public interface OnRemoveClick {
        void onRemove(FavoriteMeal meal);
    }

    public FavoritesAdapter(OnRemoveClick listener) {
        this.listener = listener;
    }

    public void submitList(List<FavoriteMeal> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteMeal meal = list.get(position);

        holder.tvMealName.setText(meal.name);
        Glide.with(holder.itemView)
                .load(meal.image)
                .into(holder.imgMeal);

        holder.btnRemove.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onRemove(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal;
        TextView tvMealName;
        ImageButton btnRemove;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
