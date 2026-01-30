package com.example.mealsapp.ui.main.fragments.profile_fragment.presneter;

import android.content.Context;

import com.example.mealsapp.data.database.dao.FavoriteMealDao;
import com.example.mealsapp.data.database.MealsDatabase;
import com.example.mealsapp.data.profile.ProfileRemoteDataSource;

import com.example.mealsapp.data.profile.ProfileRepository;
import com.example.mealsapp.utils.UserSession;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

//public class ProfilePresenterImp implements ProfilePresenter {
//    private FavoriteMealDao favoriteMealDao;
//    private CompositeDisposable disposable = new CompositeDisposable();
//Context context;
//    private ProfileView view;
//    private ProfileRepo repo;
//public ProfilePresenterImp(ProfileView view, ProfileRepo repo, Context context) {
//    this.view = view;
//    this.repo = repo;
//    this.favoriteMealDao =
//            MealsDatabase.getInstance(context).favoriteMealDao();
//    this.context = context.getApplicationContext();
//}
//
//
//    @Override
//    public void loadUserData() {
//
//        if (UserSession.isGuest(context)) {
//            view.showGuestView();
//            return;
//        }
//        FirebaseUser user = repo.getCurrentUser();
//        if (user == null || view == null) return;
//
//        view.showUserEmail(user.getEmail());
//
//        if (user.getPhotoUrl() != null) {
//            view.showUserImage(user.getPhotoUrl().toString());
//        } else {
//            view.showPlaceholderImage();
//        }
//
//        repo.getUserName(user.getUid(), new ProfileRepo.OnUserNameCallback() {
//            @Override
//            public void onSuccess(String name) {
//                if (view != null) {
//                    view.showUserName(name);
//                }
//            }
//
//            @Override
//            public void onFailure() {
//                if (view != null) {
//                    view.showUserName(user.getDisplayName());
//                }
//            }
//        });
//    }
//@Override
//public void logout() {
//
//    if (UserSession.isGuest(context)) {
//        UserSession.clear(context);
//        view.navigateToLogin();
//        return;
//    }
//
//    disposable.add(
//            favoriteMealDao.clearAll()
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(
//                            () -> repo.logout(() -> {
//                                if (view != null) {
//                                    view.navigateToLogin();
//                                }
//                            }),
//                            Throwable::printStackTrace
//                    )
//    );
//}
//
//    @Override
//    public void onDestroy() {
//        disposable.clear();
//        view = null;
//    }
//
//}
public class ProfilePresenterImp implements ProfilePresenter {

    private ProfileView view;
    private final ProfileRepository repo;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final Context context;

    public ProfilePresenterImp(
            ProfileView view,
            ProfileRepository repo,
            Context context
    ) {
        this.view = view;
        this.repo = repo;
        this.context = context.getApplicationContext();
    }

    @Override
    public void loadUserData() {
        if (UserSession.isGuest(context)) {
            view.showGuestView();
            return;
        }

        FirebaseUser user = repo.getCurrentUser();
        if (user == null || view == null) return;

        view.showUserEmail(user.getEmail());

        if (user.getPhotoUrl() != null) {
            view.showUserImage(user.getPhotoUrl().toString());
        } else {
            view.showPlaceholderImage();
        }

        repo.getUserName(user.getUid(), new ProfileRemoteDataSource.OnResult<String>() {
            @Override
            public void onSuccess(String name) {
                if (view != null) view.showUserName(name);
            }

            @Override
            public void onFailure() {
                if (view != null) view.showUserName(user.getDisplayName());
            }
        });
    }

    @Override
    public void logout() {
        if (UserSession.isGuest(context)) {
            UserSession.clear(context);
            view.navigateToLogin();
            return;
        }

        disposable.add(
                repo.logout()
                        .subscribe(
                                () -> {
                                    if (view != null) view.navigateToLogin();
                                },
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void onDestroy() {
        disposable.clear();
        view = null;
    }
}
