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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {


    private Button btnSignup;
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
                if(emailEditText_register.getText().toString().equals("")||
                passwordEditText_register.getText().toString().equals("")||
                nameEditText_register.getText().toString().equals("")||
                confirmEditText_register.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();

                }else if(!passwordEditText_register.getText().toString().equals(confirmEditText_register.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
                    userRegister();
                    startActivity(intent2);
                }

            }
        });

    }

    private void userRegister() {
        String email = emailEditText_register.getText().toString();
        String username = nameEditText_register.getText().toString();
        String password = passwordEditText_register.getText().toString();
        String password_confirm = confirmEditText_register.getText().toString();

        String url = "http://moj-rzeszow.herokuapp.com/api/auth/register/";

        JSONObject object = new JSONObject();
        try{
            object.put("email", email);
            object.put("username", username);
            object.put("password", password);
            object.put("password_confirm", password_confirm);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(RegisterActivity.this, "Pomyslnie zarejestrowano uzytkownika "+username, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Rejestracja nie powiodla sie. Sprawdz podane dane", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(objectRequest);
    }

    private void initViews() {
        btnSignup = findViewById(R.id.btnSignup);
        emailEditText_register = findViewById(R.id.emailEditText_register);
        nameEditText_register = findViewById(R.id.nameEditText_register);
        passwordEditText_register = findViewById(R.id.passwordEditText_register);
        confirmEditText_register = findViewById(R.id.confirmEditText_register);
        titleTextView = findViewById(R.id.newPostTextView);
        logoImage = findViewById(R.id.logoImage);
        barImage = findViewById(R.id.barImage);
    }
}