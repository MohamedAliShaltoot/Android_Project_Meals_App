package com.example.mealsapp.ui.main.fragments.profile_fragment.presneter;

import android.content.Context;
import com.example.mealsapp.data.calender.CalendarLocalDataSource;
import com.example.mealsapp.data.calender.CalendarSyncManager;
import com.example.mealsapp.data.calender.FirestoreCalendarRepository;
import com.example.mealsapp.data.database.MealsDatabase;
import com.example.mealsapp.data.profile.ProfileRemoteDataSource;
import com.example.mealsapp.data.profile.ProfileRepository;
import com.example.mealsapp.utils.UserSession;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    MealsDatabase db = MealsDatabase.getInstance(context);

    CalendarSyncManager calendarSyncManager =
            new CalendarSyncManager(
                    new CalendarLocalDataSource(db.plannedMealDao()),
                    new FirestoreCalendarRepository()
            );

    disposable.add(
            db.favoriteMealDao().clearAll()
                    .andThen(calendarSyncManager.clearLocal())
                    .andThen(repo.logout())
                    .subscribeOn(Schedulers.io())
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
