package com.example.mealsapp.ui.auth.login.presenter;

import android.content.Context;
import android.content.Intent;
import com.example.mealsapp.ui.auth.login.repo.LoginRepo;
import com.example.mealsapp.utils.FavoritesSyncManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenterImp implements LoginPresenter {
    private FavoritesSyncManager syncManager;

    private LoginView view;
    private LoginRepo repo;

    private static final int RC_SIGN_IN = 100;

//    public LoginPresenterImp(LoginView view, LoginRepo repo) {
//        this.view = view;
//        this.repo = repo;
//    }
    public LoginPresenterImp(LoginView view, LoginRepo repo, Context context) {
        this.view = view;
        this.repo = repo;
        this.syncManager = new FavoritesSyncManager(context);
    }

    @Override
    public void loginWithEmail(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.showError("Email & password required");
            return;
        }

        view.showLoading();
        repo.loginWithEmail(email, password, new LoginRepo.OnLoginCallback() {
//            @Override
//            public void onSuccess(FirebaseUser user) {
//                view.hideLoading();
//                view.navigateToMain();
//            }
@Override
public void onSuccess(FirebaseUser user) {
    syncFavoritesAndNavigate();
}


            @Override
            public void onFailure(String message) {
                view.hideLoading();
                view.showError(message);
            }
        });
    }

    @Override
    public void loginWithGoogle() {
        repo.getGoogleSignInIntent(intent -> view.startGoogleSignInIntent(intent));
    }

    @Override
    public void handleGoogleSignInResult(Intent data) {
        try {
            GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data)
                    .getResult(ApiException.class);

            if (account == null) {
                view.showError("Google sign in failed");
                return;
            }

            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

            view.showLoading();
            repo.loginWithGoogleCredential(credential, new LoginRepo.OnLoginCallback() {
              //  @Override
//                public void onSuccess(FirebaseUser user) {
//                    view.hideLoading();
//                    view.navigateToMain();
//                }
                @Override
                public void onSuccess(FirebaseUser user) {
                    syncFavoritesAndNavigate();
                }

                @Override
                public void onFailure(String message) {
                    view.hideLoading();
                    view.showError(message);
                }
            });

        } catch (ApiException e) {
            view.showError("Google sign in failed");
        }
    }

    @Override
    public void goToRegister() {
        view.navigateToRegister();
    }

    @Override
    public void onDestroy() {
        view = null;
    }
    private void syncFavoritesAndNavigate() {
        syncManager.syncFromFirestore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            if (view != null) {
                                view.hideLoading();
                                view.navigateToMain();
                            }
                        },
                        error -> {
                            // Fail-safe: app must still work
                            if (view != null) {
                                view.hideLoading();
                                view.navigateToMain();
                            }
                        }
                );
    }

}

