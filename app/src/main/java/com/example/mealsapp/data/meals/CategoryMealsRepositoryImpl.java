package com.example.mealsapp.data.meals;

import com.example.mealsapp.data.model.MealsResponse;
import io.reactivex.rxjava3.core.Single;

public class CategoryMealsRepositoryImpl implements CategoryMealsRepository {
    private final MealsRemoteDataSource remote;
        public CategoryMealsRepositoryImpl() {
        this.remote = new MealsRemoteDataSource();
    }
    @Override
    public Single<MealsResponse> getMealsByCategory(String category) {
        return remote.getMealsByCategory(category);
       // return RetrofitClient.getApi().getMealsByCategory(category);
    }
}


//public class CategoryMealsRepositoryImpl
//        implements CategoryMealsRepository {
//
//    private final MealsRemoteDataSource remote;
//
//    public CategoryMealsRepositoryImpl() {
//        this.remote = new MealsRemoteDataSource();
//    }
//
//    @Override
//    public Single<MealsResponse> getMealsByCategory(String category) {
//        return remote.getMealsByCategory(category);
//    }
//}