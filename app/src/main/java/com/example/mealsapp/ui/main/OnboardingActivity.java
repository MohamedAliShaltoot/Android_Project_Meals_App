package com.example.mealsapp.ui.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mealsapp.R;
import com.example.mealsapp.data.model.OnboardingItem;
import com.example.mealsapp.ui.main.adapters.OnboardingAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 onboardingViewPager;
    private Button btnNext, btnBack, btnSkip;
    private List<OnboardingItem> onboardingItems;
    private LinearLayout dotsLayout;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);
        btnSkip = findViewById(R.id.btnSkip);
        dotsLayout = findViewById(R.id.dotsLayout);
        addDots(0); // first page

        onboardingViewPager = findViewById(R.id.onboardingViewPager);

        setupOnboardingItems();

        OnboardingAdapter adapter = new OnboardingAdapter(onboardingItems);
        onboardingViewPager.setAdapter(adapter);

        updateButtons(0); // initialize buttons

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateButtons(position);
                addDots(position); // update dots
            }
        });


        btnNext.setOnClickListener(v -> {
            int nextPos = onboardingViewPager.getCurrentItem() + 1;
            if (nextPos < onboardingItems.size()) {
                onboardingViewPager.setCurrentItem(nextPos);
            } else {
                finishOnboarding();
            }
        });

        btnBack.setOnClickListener(v -> {
            int prevPos = onboardingViewPager.getCurrentItem() - 1;
            if (prevPos >= 0) {
                onboardingViewPager.setCurrentItem(prevPos);
            }
        });

        btnSkip.setOnClickListener(v -> finishOnboarding());
    }

    private void setupOnboardingItems() {
        onboardingItems = new ArrayList<>();
        onboardingItems.add(new OnboardingItem(R.drawable.ic_launcher_background, "Welcome", "This is the first screen"));
        onboardingItems.add(new OnboardingItem(R.drawable.ic_launcher_background, "Discover", "This is the second screen"));
        onboardingItems.add(new OnboardingItem(R.drawable.ic_launcher_background, "Get Started", "This is the third screen"));
    }

    private void updateButtons(int position) {
        btnBack.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        btnNext.setText(position == onboardingItems.size() - 1 ? "Start" : "Next");
    }

    private void finishOnboarding() {
        // Navigate to MainActivity or wherever
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private void addDots(int position) {
        dotsLayout.removeAllViews();
        dots = new TextView[onboardingItems.size()];

        for (int i = 0; i < onboardingItems.size(); i++) {
            dots[i] = new TextView(this);
            dots[i].setText("•"); // dot symbol
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.gray)); // inactive color
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.black)); // active color
        }
    }

}
