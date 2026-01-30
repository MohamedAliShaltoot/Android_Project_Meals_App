package com.example.mealsapp.ui.auth.register.presenter;


import com.example.mealsapp.ui.auth.register.repo.RegisterRepo;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPresenterImp implements RegisterPresenter {

    private RegisterView view;
    private RegisterRepo repo;

    public RegisterPresenterImp(RegisterView view, RegisterRepo repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void register(String name, String email, String password) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            view.showError("All fields are required");
            return;
        }

        view.disableRegisterButton();
        view.showLoading();

        repo.registerUser(name, email, password, new RegisterRepo.OnRegisterCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                view.hideLoading();
                view.enableRegisterButton();
                view.navigateToMain();
            }


            @Override
            public void onFailure(String message) {
                view.hideLoading();
                view.enableRegisterButton();
                view.showError(message);
            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}

