package com.example.mealsapp.ui.main.fragments.profile_fragment.presneter;

import android.content.Context;

import com.example.mealsapp.data.database.localDatabase.FavoriteMealDao;
import com.example.mealsapp.data.database.localDatabase.MealsDatabase;
import com.example.mealsapp.ui.main.fragments.profile_fragment.repo.ProfileRepo;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImp implements ProfilePresenter {
    private FavoriteMealDao favoriteMealDao;
    private CompositeDisposable disposable = new CompositeDisposable();

    private ProfileView view;
    private ProfileRepo repo;

//    public ProfilePresenterImp(ProfileView view, ProfileRepo repo) {
//        this.view = view;
//        this.repo = repo;
//    }
public ProfilePresenterImp(ProfileView view, ProfileRepo repo, Context context) {
    this.view = view;
    this.repo = repo;
    this.favoriteMealDao =
            MealsDatabase.getInstance(context).favoriteMealDao();
}


    @Override
    public void loadUserData() {
        FirebaseUser user = repo.getCurrentUser();
        if (user == null || view == null) return;

        view.showUserEmail(user.getEmail());

        if (user.getPhotoUrl() != null) {
            view.showUserImage(user.getPhotoUrl().toString());
        } else {
            view.showPlaceholderImage();
        }

        repo.getUserName(user.getUid(), new ProfileRepo.OnUserNameCallback() {
            @Override
            public void onSuccess(String name) {
                if (view != null) {
                    view.showUserName(name);
                }
            }

            @Override
            public void onFailure() {
                if (view != null) {
                    view.showUserName(user.getDisplayName());
                }
            }
        });
    }

//    @Override
//    public void logout() {
//        repo.logout(() -> {
//            if (view != null) {
//                view.navigateToLogin();
//            }
//        });
//    }
@Override
public void logout() {
    disposable.add(
            favoriteMealDao.clearAll()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            () -> repo.logout(() -> {
                                if (view != null) {
                                    view.navigateToLogin();
                                }
                            }),
                            Throwable::printStackTrace
                    )
    );
}

//    @Override
//    public void onDestroy() {
//        view = null;
//    }
    @Override
    public void onDestroy() {
        disposable.clear();
        view = null;
    }

}

