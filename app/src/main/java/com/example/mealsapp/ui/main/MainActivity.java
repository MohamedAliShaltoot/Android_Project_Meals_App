package com.example.mealsapp.ui.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mealsapp.R;
import com.example.mealsapp.ui.main.fragments.home_fragment.HomeFragment;
import com.example.mealsapp.ui.main.fragments.profile_fragment.ProfileFragment;
import com.example.mealsapp.ui.main.fragments.search_fragment.SearchFragment;
import com.example.mealsapp.ui.main.fragments.fav_fragment.Favoritessfragment;
import com.example.mealsapp.utils.AppPrefs;
import com.example.mealsapp.utils.AppSnackbar;
import com.example.mealsapp.utils.SnackType;
import com.example.mealsapp.utils.UserSession;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        NavigationUI.setupWithNavController(bottomNav, navController);


        userName = UserSession.getUserName(this);

        if (AppPrefs.isFirstTime(this)) {
            AppSnackbar.show(
                    bottomNav,
                    "Welcome " + userName ,
                    SnackType.SUCCESS
            );
            AppPrefs.setNotFirstTime(this);
        } else {
            AppSnackbar.show(
                    bottomNav,
                    "Welcome back " + userName + " Nice to see you again.",
                    SnackType.INFO
            );
        }


    }
}

