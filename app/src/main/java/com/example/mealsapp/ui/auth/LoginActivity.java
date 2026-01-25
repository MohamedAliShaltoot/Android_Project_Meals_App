package com.example.mealsapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealsapp.R;
import com.example.mealsapp.ui.main.MainActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;
    ProgressBar progressBar;
    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient googleSignInClient;
    Button btnGoogleLogin;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvGoRegister);
        progressBar = findViewById(R.id.progressBar);
      btnGoogleLogin = findViewById(R.id.btnLoginWithEmail);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        btnGoogleLogin.setOnClickListener(v -> signInWithGoogle());

        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> loginUser());

        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            try {
                GoogleSignInAccount account =
                        GoogleSignIn.getSignedInAccountFromIntent(data)
                                .getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {

                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email & password required", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        progressBar.setVisibility(View.VISIBLE);

        auth.signInWithCredential(credential)
                .addOnSuccessListener(result -> {

                    FirebaseUser user = auth.getCurrentUser();
                    if (user == null) {
                        progressBar.setVisibility(View.GONE);
                        return;
                    }

                    String uid = user.getUid();

                    Map<String, Object> data = new HashMap<>();
                    data.put("uid", uid);
                    data.put("name", user.getDisplayName());
                    data.put("email", user.getEmail());

                    FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(uid)
                            .set(data, SetOptions.merge())
                            .addOnSuccessListener(unused -> {
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            });

                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }


}

