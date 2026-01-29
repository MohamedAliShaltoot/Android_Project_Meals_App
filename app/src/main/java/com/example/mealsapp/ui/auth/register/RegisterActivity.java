package com.example.mealsapp.ui.auth.register;

import android.content.Intent;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mealsapp.R;
import com.example.mealsapp.ui.auth.register.presenter.RegisterPresenter;
import com.example.mealsapp.ui.auth.register.presenter.RegisterPresenterImp;
import com.example.mealsapp.ui.auth.register.presenter.RegisterView;
import com.example.mealsapp.ui.auth.register.repo.RegisterRepoImp;
import com.example.mealsapp.ui.main.MainActivity;


public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private EditText etName, etEmail, etPassword;
    private Button btnRegister;
    private ProgressBar progressBar;

    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);

        presenter = new RegisterPresenterImp(this, new RegisterRepoImp());

        btnRegister.setOnClickListener(v ->
                presenter.register(
                        etName.getText().toString().trim(),
                        etEmail.getText().toString().trim(),
                        etPassword.getText().toString().trim()
                )
        );
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

    @Override
    public void enableRegisterButton() {
        btnRegister.setEnabled(true);
    }

    @Override
    public void disableRegisterButton() {
        btnRegister.setEnabled(false);
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
}

