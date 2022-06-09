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

public class RegisterActivity extends AppCompatActivity {


    private Button btnSignup, btnBack;
    private EditText emailEditText_register, passwordEditText_register, nameEditText_register, confirmEditText_register;
    private TextView titleTextView;
    private ImageView logoImage, barImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent2);
                Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(RegisterActivity.this, "Going back", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        btnSignup = findViewById(R.id.btnSignup);
        btnBack = findViewById(R.id.btnBack);

        emailEditText_register = findViewById(R.id.emailEditText_register);
        nameEditText_register = findViewById(R.id.nameEditText_register);
        passwordEditText_register = findViewById(R.id.passwordEditText_register);
        confirmEditText_register = findViewById(R.id.confirmEditText_register);

        titleTextView = findViewById(R.id.newPostTextView);
        logoImage = findViewById(R.id.logoImage);
        barImage = findViewById(R.id.barImage);
    }
}