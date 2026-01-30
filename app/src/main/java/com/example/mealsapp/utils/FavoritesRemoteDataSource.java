package com.example.mealsapp.utils;

import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FavoritesRemoteDataSource {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final String userId;

    public FavoritesRemoteDataSource(String userId) {
        this.userId = userId;
    }

    public Completable addFavorite(FavoriteMeal meal) {
        return Completable.create(emitter ->
                firestore.collection("users")
                        .document(userId)
                        .collection("favorites")
                        .document(meal.idMeal)
                        .set(meal)
                        .addOnSuccessListener(unused -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Completable removeFavorite(String mealId) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return Completable.complete(); // silently ignore
        }
        return Completable.create(emitter ->
                firestore.collection("users")
                        .document(userId)
                        .collection("favorites")
                        .document(mealId)
                        .delete()
                        .addOnSuccessListener(unused -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Single<List<FavoriteMeal>> getAllFavorites() {
        return Single.create(emitter ->
                firestore.collection("users")
                        .document(userId)
                        .collection("favorites")
                        .get()
                        .addOnSuccessListener(snapshot ->
                                emitter.onSuccess(
                                        snapshot.toObjects(FavoriteMeal.class)
                                )
                        )
                        .addOnFailureListener(emitter::onError)
        );
    }
}

