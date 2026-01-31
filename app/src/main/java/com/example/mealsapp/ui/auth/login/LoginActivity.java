package com.example.mealsapp.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mealsapp.R;
import com.example.mealsapp.data.calender.CalendarLocalDataSource;
import com.example.mealsapp.data.calender.CalendarSyncManager;
import com.example.mealsapp.data.calender.FirestoreCalendarRepository;
import com.example.mealsapp.data.database.MealsDatabase;
import com.example.mealsapp.data.favorites.FavoritesSyncManager;
import com.example.mealsapp.data.favorites.SyncFavoritesUseCase;
import com.example.mealsapp.data.favorites.SyncFavoritesUseCaseImpl;
import com.example.mealsapp.ui.auth.login.presenter.LoginPresenter;
import com.example.mealsapp.ui.auth.login.presenter.LoginPresenterImp;
import com.example.mealsapp.ui.auth.login.presenter.LoginView;
import com.example.mealsapp.ui.auth.login.repo.LoginRepoImp;
import com.example.mealsapp.ui.auth.register.RegisterActivity;
import com.example.mealsapp.ui.main.MainActivity;
import com.example.mealsapp.utils.UserSession;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnGoogleLogin;
    private TextView tvRegister;
    private ProgressBar progressBar;
    TextView tvGuest;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogleLogin = findViewById(R.id.btnLoginWithEmail);
        tvRegister = findViewById(R.id.tvGoRegister);
        progressBar = findViewById(R.id.progressBar);
         tvGuest = findViewById(R.id.tvGoToGuestMode);

        tvGuest.setOnClickListener(v -> {
            UserSession.loginGuest(this);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        FavoritesSyncManager manager =
                new FavoritesSyncManager(getApplicationContext());

        SyncFavoritesUseCase syncUseCase =
                new SyncFavoritesUseCaseImpl(manager);
        CalendarSyncManager calendarSyncManager =
                new CalendarSyncManager(
                        new CalendarLocalDataSource(
                                MealsDatabase.getInstance(this).plannedMealDao()
                        ),
                        new FirestoreCalendarRepository()
                );
        presenter = new LoginPresenterImp(
                this,
                new LoginRepoImp(this),
                syncUseCase,
                calendarSyncManager
        );

        btnLogin.setOnClickListener(v ->
                presenter.loginWithEmail(
                        etEmail.getText().toString().trim(),
                        etPassword.getText().toString().trim()
                )
        );

        btnGoogleLogin.setOnClickListener(v -> presenter.loginWithGoogle());

        tvRegister.setOnClickListener(v -> presenter.goToRegister());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            presenter.handleGoogleSignInResult(data);
        }
    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(android.view.View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    private void saveUserName() {
        com.example.mealsapp.utils.UserSession.saveUser(this);
    }

    @Override
    public void navigateToMain() {
        saveUserName();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void navigateToRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void startGoogleSignInIntent(Intent intent) {
        startActivityForResult(intent, 100);
    }
}


