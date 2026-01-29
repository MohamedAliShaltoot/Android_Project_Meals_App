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
import com.example.mealsapp.utils.AppSnackbar;
import com.example.mealsapp.utils.SnackType;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

//    private Fragment homeFragment;
//    //private Fragment favoritesFragment;
//    private Fragment favoritessfragment;
//    private Fragment profileFragment;
//    private Fragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppSnackbar.show(
                this.findViewById(R.id.bottomNav),
                "Welcome to Meals App",
                SnackType.INFO
        );
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        NavigationUI.setupWithNavController(bottomNav, navController);


        // BottomNavigationView nav = findViewById(R.id.bottomNav);

       // homeFragment = new HomeFragment();
        //favoritesFragment = new FavoritesFragment();
      //  favoritessfragment = new Favoritessfragment();
     //   profileFragment = new ProfileFragment();
       // settingsFragment = new SearchFragment();

        //to make home load once
       // loadFragment(homeFragment);

//        nav.setOnItemSelectedListener(item -> {
//            int id = item.getItemId();
//
//            if (id == R.id.nav_home) {
//                loadFragment(homeFragment);
//            } else if (id == R.id.nav_fav) {
//                loadFragment(favoritessfragment);
//            } else if (id == R.id.nav_profile) {
//                loadFragment(profileFragment);
//            } else if (id == R.id.nav_settings) {
//                loadFragment(settingsFragment);
//            }
//            return true;
//        });
    }

//    void loadFragment(Fragment fragment) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.container, fragment)
//                .commit();
//    }
}

