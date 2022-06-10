package com.example.myrzeszow_w;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private EditText emailEditText_login, passwordEditText_login;
    private TextView forgotTextView, titleTextView;
    private ImageView logoImage, barImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailEditText_login.getText().length() >= 0 && passwordEditText_login.getText().length() >= 0) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Username or Password are not filled in", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initViews() {
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        emailEditText_login = findViewById(R.id.postCommentEdit);
        passwordEditText_login = findViewById(R.id.postBodyEdit);

        forgotTextView = findViewById(R.id.forgotTextView);
        titleTextView = findViewById(R.id.newPostTextView);

        logoImage = findViewById(R.id.logoImage);
        barImage = findViewById(R.id.barImage);
    }
}