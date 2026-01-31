package com.example.mealsapp.data.calender;

import com.example.mealsapp.data.database.planner_dao.PlannedMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirestoreCalendarRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    private String uid() {
        return auth.getCurrentUser().getUid();
    }

    public Completable addMeal(PlannedMeal meal) {
        return Completable.create(emitter ->
                db.collection("users")
                        .document(uid())
                        .collection("calendar")
                        .document(meal.id) // unique id
                        .set(meal)
                        .addOnSuccessListener(unused -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Completable removeMeal(String id) {
        return Completable.create(emitter ->
                db.collection("users")
                        .document(uid())
                        .collection("calendar")
                        .document(id)
                        .delete()
                        .addOnSuccessListener(unused -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Single<List<PlannedMeal>> getAllMeals() {
        return Single.create(emitter ->
                db.collection("users")
                        .document(uid())
                        .collection("calendar")
                        .get()
                        .addOnSuccessListener(snapshot ->
                                emitter.onSuccess(snapshot.toObjects(PlannedMeal.class))
                        )
                        .addOnFailureListener(emitter::onError)
        );
    }
}
