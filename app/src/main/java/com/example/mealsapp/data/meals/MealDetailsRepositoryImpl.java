package com.example.mealsapp.data.meals;

import com.example.mealsapp.data.database.dao.FavoriteMeal;
import com.example.mealsapp.data.database.dao.FavoriteMealDao;
import com.example.mealsapp.data.model.MealsResponse;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

//public class MealDetailsRepositoryImpl implements MealDetailsRepository {
//private final FavoriteMealDao dao;
//    private final FirestoreFavoritesRepository firestoreRepo;
//
//    public MealDetailsRepositoryImpl(FavoriteMealDao dao) {
//        this.dao = dao;
//        this.firestoreRepo = new FirestoreFavoritesRepository();
//    }
//
//    @Override
//    public Single<MealsResponse> getMealDetails(String mealId) {
//        return RetrofitClient.getApi().getMealDetails(mealId);
//    }
//
//    @Override
//    public Single<Boolean> isFavorite(String mealId) {
//        return dao.isFavorite(mealId);
//    }
//
//@Override
//public Completable addFavorite(FavoriteMeal meal) {
//    return dao.insert(meal)
//            .andThen(firestoreRepo.addFavorite(meal));
//}
//
//    @Override
//    public Completable removeFavorite(FavoriteMeal meal) {
//        return dao.delete(meal)
//                .andThen(firestoreRepo.removeFavorite(meal.idMeal));
//    }
//
//}

import com.example.mealsapp.data.favorites.FavoritesLocalDataSource;
import com.example.mealsapp.data.favorites.FavoritesRemoteDataSource;


public class MealDetailsRepositoryImpl implements MealDetailsRepository {

    private final MealsRemoteDataSource mealsRemote;
    private final FavoritesLocalDataSource favoritesLocal;
    private final FavoritesRemoteDataSource favoritesRemote;

    public MealDetailsRepositoryImpl(FavoriteMealDao dao) {
        this.mealsRemote = new MealsRemoteDataSource();
        this.favoritesLocal = new FavoritesLocalDataSource(dao);
        this.favoritesRemote = new FavoritesRemoteDataSource();
    }

    @Override
    public Single<MealsResponse> getMealDetails(String mealId) {
        return mealsRemote.getMealDetails(mealId);
    }

    @Override
    public Single<Boolean> isFavorite(String mealId) {
        return favoritesLocal.isFavorite(mealId);
    }

    @Override
    public Completable addFavorite(FavoriteMeal meal) {
        return favoritesLocal.insert(meal)
                .andThen(favoritesRemote.addFavorite(meal));
    }

    @Override
    public Completable removeFavorite(FavoriteMeal meal) {
        return favoritesLocal.delete(meal)
                .andThen(favoritesRemote.removeFavorite(meal.idMeal));
    }
}