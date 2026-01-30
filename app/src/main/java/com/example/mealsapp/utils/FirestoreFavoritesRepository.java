package com.example.mealsapp.utils;

import com.example.mealsapp.data.database.localDatabase.FavoriteMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirestoreFavoritesRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    private String uid() {
        return auth.getCurrentUser().getUid();
    }

    public Completable addFavorite(FavoriteMeal meal) {
        return Completable.create(emitter ->
                db.collection("users")
                        .document(uid())
                        .collection("favorites")
                        .document(meal.idMeal)
                        .set(meal)
                        .addOnSuccessListener(unused -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Completable removeFavorite(String mealId) {
        return Completable.create(emitter ->
                db.collection("users")
                        .document(uid())
                        .collection("favorites")
                        .document(mealId)
                        .delete()
                        .addOnSuccessListener(unused -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Single<List<FavoriteMeal>> getAllFavorites() {
        return Single.create(emitter ->
                db.collection("users")
                        .document(uid())
                        .collection("favorites")
                        .get()
                        .addOnSuccessListener(snapshot -> {
                            List<FavoriteMeal> list =
                                    snapshot.toObjects(FavoriteMeal.class);
                            emitter.onSuccess(list);
                        })
                        .addOnFailureListener(emitter::onError)
        );
    }
}

