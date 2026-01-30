package com.example.mealsapp.ui.main.fragments.fav_fragment.presenter;

import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.example.mealsapp.data.favorites.FavoritesRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class FavoritesPresenterImpl implements FavoritesPresenter {

    private final FavoritesView view;
    private final FavoritesRepository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public FavoritesPresenterImpl(
            FavoritesView view,
            FavoritesRepository repository
    ) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadFavorites() {
        disposable.add(
                repository.getAllFavorites()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                view::showFavorites,
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void removeFavorite(FavoriteMeal meal) {
        disposable.add(
                repository.removeFavorite(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.showRemoveMessage(meal.name),
                                Throwable::printStackTrace
                        )
        );
    }

    public void clear() {
        disposable.clear();
    }
}
