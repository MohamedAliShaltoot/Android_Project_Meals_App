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
import com.example.mealsapp.ui.auth.login.presenter.LoginPresenter;
import com.example.mealsapp.ui.auth.login.presenter.LoginPresenterImp;
import com.example.mealsapp.ui.auth.login.presenter.LoginView;
import com.example.mealsapp.ui.auth.login.repo.LoginRepoImp;
import com.example.mealsapp.ui.auth.register.RegisterActivity;
import com.example.mealsapp.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnGoogleLogin;
    private TextView tvRegister;
    private ProgressBar progressBar;

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

        //presenter = new LoginPresenterImp(this, new LoginRepoImp(this));
        presenter = new LoginPresenterImp(
                this,
                new LoginRepoImp(this),
                this // context
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


