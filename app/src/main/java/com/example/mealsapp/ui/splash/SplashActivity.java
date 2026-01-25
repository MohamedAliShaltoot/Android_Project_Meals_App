package com.example.mealsapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealsapp.R;
import com.example.mealsapp.ui.auth.LoginActivity;
import com.example.mealsapp.ui.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        LottieAnimationView lottie = findViewById(R.id.lottieSplash);
        lottie.playAnimation();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            if (auth.getCurrentUser() == null) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return;
            }

            auth.getCurrentUser()
                    .reload()
                    .addOnSuccessListener(unused -> {
                        // User still exists
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // User deleted from Firebase Console
                        auth.signOut();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    });

        }, SPLASH_DURATION);


    }
}
