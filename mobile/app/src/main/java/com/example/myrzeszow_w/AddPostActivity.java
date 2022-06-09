package com.example.myrzeszow_w;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {

    private ImageView barImage;
    private TextView newPostTextView, titleText, postContentText,estateName,categoryName;
    private EditText postTitleEdit,postBodyEdit;
    private Spinner estateSpinner, categorySpinner;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //region init views region
        barImage = findViewById(R.id.barImage);
        newPostTextView = findViewById(R.id.newPostTextView);
        titleText = findViewById(R.id.titleText);
        postContentText = findViewById(R.id.postContentText);
        estateName = findViewById(R.id.estateName);
        categoryName = findViewById(R.id.categoryName);
        postTitleEdit = findViewById(R.id.postTitleEdit);
        postBodyEdit = findViewById(R.id.postBodyEdit);
        estateSpinner = findViewById(R.id.estateSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        btnPost = findViewById(R.id.btnPost);

        //endregion region

        //region spinner data hardcoded for now

        String[] estates = new String[]{"Drabinianka", "Baranowka", "Pobitno"};
        String[] categories = new String[]{"Imprezy", "Zmiany", "Sprzedam","Kupie"};

        ArrayAdapter<String> estatesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estates);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        estateSpinner.setAdapter(estatesAdapter);
        categorySpinner.setAdapter(categoriesAdapter);
        //endregion

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPost();
//                Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
//                startActivity(intent);


    }

    private void addNewPost() {


        String title = postTitleEdit.getText().toString();
        String body = postBodyEdit.getText().toString();
//        String estate = estateSpinner.getSelectedItem().toString();
//        String category = categorySpinner.getSelectedItem().toString();\
        int estate =1;
        int category = 1;

//       long estate = estateSpinner.getSelectedItemId();
//       long category = categorySpinner.getSelectedItemId();

        String url = "http://moj-rzeszow.herokuapp.com/api/posts/";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("estate", estate);
            jsonObject.put("category", category);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(AddPostActivity.this, "Response "+response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddPostActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put("Context-Type", "application/json");
                hashMap.put("Authorization","Token ca4d876643c57786168849699a9ac4f651d6832c");
                return hashMap;
            }
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(AddPostActivity.this, "Response "+response.trim(), Toast.LENGTH_SHORT).show();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(AddPostActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @NonNull
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError{
//                HashMap<String, String> hashMap = new HashMap<String, String>();
//                hashMap.put("Context-Type", "application/json");
//                hashMap.put("Authorization","Token ca4d876643c57786168849699a9ac4f651d6832c");
//                hashMap.put("title", title);
//                hashMap.put("estate", test);
//                hashMap.put("category",);
//                return hashMap;
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Content-Type", "text/plain; charset=utf-8");
//                params.put("Authorization","Token ca4d876643c57786168849699a9ac4f651d6832c");
//                params.put("title", title);
////                params.put("estate", estate);
////                params.put("category", category);
//                return params;

        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddPostActivity.this);
        requestQueue.add(objectRequest);
    }
        });
    }
}