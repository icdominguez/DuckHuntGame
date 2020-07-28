package com.icdominguez.duckhunt.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.icdominguez.duckhunt.R;
import com.icdominguez.duckhunt.common.Constants;
import com.icdominguez.duckhunt.models.User;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    Button btnStart;
    String username;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        events();

        db = FirebaseFirestore.getInstance();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "pixel.ttf");
        etUsername.setTypeface(typeface);
        btnStart.setTypeface(typeface);

    }

    private void events() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etUsername.getText().toString();
                if(username.isEmpty()) {
                    etUsername.setError(getString(R.string.login_username_empty));
                } else {
                    addUserToFireStore();
                }
            }
        });
    }

    private void findViews() {
        etUsername = findViewById(R.id.editTextUsername);
        btnStart = findViewById(R.id.buttonStart);
    }

    private void addUserAndStart() {

        db.collection("users").whereEqualTo("username", username).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size() > 0) {
                    etUsername.setError(getString(R.string.login_existing_user));
                } else {
                    addUserToFireStore();
                }
            }
        });


    }

    private void addUserToFireStore() {
        User newUser = new User(username, 0);

        db.collection("users").add(newUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                etUsername.setText("");
                Intent intentGame = new Intent(LoginActivity.this, GameActivity.class);
                intentGame.putExtra(Constants.EXTRA_USERNAME, username);
                intentGame.putExtra(Constants.EXTRA_ID, documentReference.getId());
                startActivity(intentGame);
            }
        });
    }
}