package com.example.mealsapp.data.home;
import com.example.mealsapp.data.model.CategoriesResponse;
import com.example.mealsapp.data.model.MealsResponse;
import io.reactivex.rxjava3.core.Single;
import com.example.mealsapp.data.meals.MealsRemoteDataSource;


public class HomeRepositoryImpl implements HomeRepository {

    private final MealsRemoteDataSource remote;

    public HomeRepositoryImpl() {
        this.remote = new MealsRemoteDataSource();
    }

    @Override
    public Single<CategoriesResponse> getCategories() {
        return remote.getCategories();
    }

    @Override
    public Single<MealsResponse> getRandomMeal() {
        return remote.getRandomMeal();
    }
}