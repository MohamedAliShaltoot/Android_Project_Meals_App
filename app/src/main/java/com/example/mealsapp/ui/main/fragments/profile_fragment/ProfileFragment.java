package com.example.mealsapp.ui.main.fragments.profile_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.ui.auth.login.LoginActivity;
import com.example.mealsapp.ui.main.fragments.profile_fragment.presneter.ProfilePresenter;
import com.example.mealsapp.ui.main.fragments.profile_fragment.presneter.ProfilePresenterImp;
import com.example.mealsapp.ui.main.fragments.profile_fragment.presneter.ProfileView;
import com.example.mealsapp.ui.main.fragments.profile_fragment.repo.ProfileRepoImp;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ProfileFragment extends Fragment implements ProfileView {

    private TextView tvName, tvEmail;
    private ImageView imgProfile;
    private MaterialButton btnLogout;

    private ProfilePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        imgProfile = view.findViewById(R.id.imgProfile);
        btnLogout = view.findViewById(R.id.btnLogout);

       // presenter = new ProfilePresenterImp(this, new ProfileRepoImp());
        presenter = new ProfilePresenterImp(
                this,
                new ProfileRepoImp(),
                requireContext()
        );

        btnLogout.setOnClickListener(v -> showLogoutDialog());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadUserData();
    }

    private void showLogoutDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setIcon(R.drawable.ic_logout)
                .setPositiveButton("Yes", (dialog, which) -> presenter.logout())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }


    @Override
    public void showUserName(String name) {
        tvName.setText(name);
    }

    @Override
    public void showUserEmail(String email) {
        tvEmail.setText(email);
    }

    @Override
    public void showUserImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.place_holder)
                .into(imgProfile);
    }

    @Override
    public void showPlaceholderImage() {
        imgProfile.setImageResource(R.drawable.place_holder);
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }
}


