package com.example.mealsapp.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mealsapp.R;
import com.google.android.material.snackbar.Snackbar;

public class AppSnackbar {

    public static void show(View anchor, String message, SnackType type) {

        Snackbar snackbar = Snackbar.make(anchor, "", getDuration(type));
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundTintList(null);
        snackbarView.setBackground(
                anchor.getContext().getDrawable(getBackground(type))
        );


        ViewGroup.LayoutParams params = snackbarView.getLayoutParams();
        if (params instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) params).gravity = Gravity.TOP;
        }

        View custom = LayoutInflater.from(anchor.getContext())
                .inflate(R.layout.view_snackbar, null);

        ImageView icon = custom.findViewById(R.id.snackIcon);
        TextView text = custom.findViewById(R.id.snackText);

        icon.setImageResource(getIcon(type));
        text.setText(message);

        Snackbar.SnackbarLayout layout =
                (Snackbar.SnackbarLayout) snackbarView;
        layout.removeAllViews();
        layout.addView(custom);

        snackbarView.setAlpha(0f);
        snackbarView.animate().alpha(1f).setDuration(300).start();

        snackbar.show();
    }

    private static int getBackground(SnackType type) {
        switch (type) {
            case SUCCESS: return R.drawable.snackbar_success;
            case ERROR: return R.drawable.snackbar_error;
            case WARNING: return R.drawable.snackbar_warning;
            default: return R.drawable.snackbar_info;
        }
    }

    private static int getIcon(SnackType type) {
        switch (type) {
            case SUCCESS: return R.drawable.ic_check;
            case ERROR: return R.drawable.ic_error;
            case WARNING: return R.drawable.ic_warning;
            default: return R.drawable.ic_info;
        }
    }

    private static int getDuration(SnackType type) {
        return type == SnackType.ERROR
                ? Snackbar.LENGTH_LONG
                : Snackbar.LENGTH_SHORT;
    }
}
