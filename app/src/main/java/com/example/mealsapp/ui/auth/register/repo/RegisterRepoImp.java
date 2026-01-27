package com.example.mealsapp.ui.auth.register.repo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RegisterRepoImp implements RegisterRepo {

    private final FirebaseAuth auth;
    private final FirebaseFirestore db;

    public RegisterRepoImp() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void registerUser(String name, String email, String password, OnRegisterCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user == null) {
                        callback.onFailure("User creation failed");
                        return;
                    }

                    String uid = user.getUid();

                    Map<String, Object> userData = new HashMap<>();
                    userData.put("uid", uid);
                    userData.put("name", name);
                    userData.put("email", email);

                    db.collection("users")
                            .document(uid)
                            .set(userData)
                            .addOnSuccessListener(unused -> callback.onSuccess(user))
                            .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }
}

