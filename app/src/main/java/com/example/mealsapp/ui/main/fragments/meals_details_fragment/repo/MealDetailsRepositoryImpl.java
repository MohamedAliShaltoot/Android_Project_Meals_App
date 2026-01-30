package com.example.mealsapp.ui.main.fragments.meals_details_fragment.repo;

import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.example.mealsapp.data.database.localDatabase.FavoriteMealDao;
import com.example.mealsapp.data.model.MealsResponse;
import com.example.mealsapp.data.network.RetrofitClient;
import com.example.mealsapp.utils.FirestoreFavoritesRepository;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class MealDetailsRepositoryImpl implements MealDetailsRepository {

//    private final FavoriteMealDao dao;
//
//    public MealDetailsRepositoryImpl(FavoriteMealDao dao) {
//        this.dao = dao;
//    }
private final FavoriteMealDao dao;
    private final FirestoreFavoritesRepository firestoreRepo;

    public MealDetailsRepositoryImpl(FavoriteMealDao dao) {
        this.dao = dao;
        this.firestoreRepo = new FirestoreFavoritesRepository();
    }

    @Override
    public Single<MealsResponse> getMealDetails(String mealId) {
        return RetrofitClient.getApi().getMealDetails(mealId);
    }

    @Override
    public Single<Boolean> isFavorite(String mealId) {
        return dao.isFavorite(mealId);
    }

//    @Override
//    public Completable addFavorite(FavoriteMeal meal) {
//        return dao.insert(meal);
//    }
//
//    @Override
//    public Completable removeFavorite(FavoriteMeal meal) {
//        return dao.delete(meal);
//    }
@Override
public Completable addFavorite(FavoriteMeal meal) {
    return dao.insert(meal)
            .andThen(firestoreRepo.addFavorite(meal));
}

    @Override
    public Completable removeFavorite(FavoriteMeal meal) {
        return dao.delete(meal)
                .andThen(firestoreRepo.removeFavorite(meal.idMeal));
    }

}

