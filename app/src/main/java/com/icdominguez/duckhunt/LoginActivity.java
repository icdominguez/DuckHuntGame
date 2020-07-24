package com.icdominguez.duckhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.icdominguez.duckhunt.common.Constants;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    Button btnStart;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        events();

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
                    etUsername.setText("");

                    Intent intentGame = new Intent(LoginActivity.this, GameActivity.class);
                    intentGame.putExtra(Constants.EXTRA_USERNAME, username);
                    startActivity(intentGame);
                }
            }
        });
    }

    private void findViews() {
        etUsername = findViewById(R.id.editTextUsername);
        btnStart = findViewById(R.id.buttonStart);
    }
}