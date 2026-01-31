package com.example.mealsapp.ui.main.fragments.meals_details_fragment.presenter;

import android.app.DatePickerDialog;
import android.content.Context;

import com.example.mealsapp.data.calender.CalendarSyncManager;
import com.example.mealsapp.data.database.planner_dao.PlannedMeal;
import com.example.mealsapp.data.database.dao.FavoriteMeal;
import com.example.mealsapp.data.model.Meal;
import com.example.mealsapp.data.meals.MealDetailsRepository;
import com.example.mealsapp.utils.SnackType;
import com.example.mealsapp.utils.UserSession;

import java.util.Calendar;
import java.util.UUID;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImpl
        implements MealDetailsContract.Presenter {

    private MealDetailsContract.View view;
    private final MealDetailsRepository repo;
    private final CompositeDisposable disposable = new CompositeDisposable();
Context context;
    private Meal currentMeal;
    private boolean isFavorite;
    CalendarSyncManager calendarSyncManager;
    public MealDetailsPresenterImpl(
            MealDetailsContract.View view,
            MealDetailsRepository repo,
            Context context,
            CalendarSyncManager calendarSyncManager
    ) {
        this.view = view;
        this.repo = repo;
        this.context = context;
        this.calendarSyncManager = calendarSyncManager;

    }

@Override
public void loadMeal(String mealId) {

    // Always load meal details
    disposable.add(
            repo.getMealDetails(mealId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response -> {
                                if (response.getMeals() == null ||
                                        response.getMeals().isEmpty()) return;

                                currentMeal = response.getMeals().get(0);
                                view.showMeal(currentMeal);

                                // Only check favorite if NOT guest
                                if (!UserSession.isGuest(context)) {
                                    checkFavorite();
                                } else {
                                    view.showFavoriteState(false);
                                }
                            },
                            throwable -> view.showError(throwable.getMessage())
                    )
    );

    // Guest UI hint
    if (UserSession.isGuest(context)) {
        view.showGuestView();
    }
}
    @Override
    public void onAddToCalendarClicked() {

        if (UserSession.isGuest(context)) {
            view.showMessage("Login to use calendar", SnackType.INFO);
            return;
        }

        Calendar today = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                context,
                (datePicker, year, month, day) -> {

                    Calendar selected = Calendar.getInstance();
                    selected.set(year, month, day);

                    if (!isDateInCurrentWeek(selected)) {
                        view.showError("You can only plan meals for this week");
                        return;
                    }

                    PlannedMeal plannedMeal = new PlannedMeal(
                            UUID.randomUUID().toString(),
                            currentMeal.getIdMeal(),
                            currentMeal.getStrMeal(),
                            currentMeal.getStrMealThumb(),
                            selected.getTimeInMillis()
                    );

                    disposable.add( calendarSyncManager.addMeal(plannedMeal)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> view.showMessage("Meal added to calendar", SnackType.SUCCESS),
                                    e -> view.showError("Failed to add meal")
                            ));
                },
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }
    private boolean isDateInCurrentWeek(Calendar selected) {
        Calendar now = Calendar.getInstance();

        now.set(Calendar.DAY_OF_WEEK, now.getFirstDayOfWeek());
        long startWeek = now.getTimeInMillis();

        now.add(Calendar.DAY_OF_WEEK, 6);
        long endWeek = now.getTimeInMillis();

        long selectedTime = selected.getTimeInMillis();

        return selectedTime >= startWeek && selectedTime <= endWeek;
    }
    private void checkFavorite() {
        disposable.add(
                repo.isFavorite(currentMeal.getIdMeal())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    isFavorite = result;
                                    view.showFavoriteState(result);
                                },
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void toggleFavorite() {
        if (UserSession.isGuest(context)) {
            view.showGuestView();
            return;
        }

        if (currentMeal == null) return;

        FavoriteMeal fav = new FavoriteMeal(
                currentMeal.getIdMeal(),
                currentMeal.getStrMeal(),
                currentMeal.getStrMealThumb()
        );

        disposable.add(
                (isFavorite
                        ? repo.removeFavorite(fav)
                        : repo.addFavorite(fav))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isFavorite = !isFavorite;
                                    view.showFavoriteState(isFavorite);
                                    view.showMessage(
                                            currentMeal.getStrMeal() +
                                                    (isFavorite
                                                            ? " Added to favorites"
                                                            : " Removed from favorites"),
                                            isFavorite
                                                    ? SnackType.SUCCESS
                                                    : SnackType.INFO
                                    );
                                },
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void clear() {
        disposable.clear();
        view = null;
    }
}

