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
//                if (emailEditText_login.getText().length() >= 0
//                        && passwordEditText_login.getText().length() >= 0) {
                if(emailEditText_login.getText().toString().equals("")|| passwordEditText_login.getText().toString().equals("")){

                    Toast.makeText(LoginActivity.this, "Both fields are required", Toast.LENGTH_SHORT).show();

                } else
                {
                    userLogin();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
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

    private void userLogin() {
        String email = emailEditText_login.getText().toString();
        String password = passwordEditText_login.getText().toString();

        String url = "http://moj-rzeszow.herokuapp.com/api/auth/login/";

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                try {
                    String auth = response.getString("token");
                    Toast.makeText(LoginActivity.this, ""+auth, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("token", auth);
                    Intent adapterToken = new Intent(LoginActivity.this, AdapterPost.class);
                    adapterToken.putExtra("token", auth);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(objectRequest);
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