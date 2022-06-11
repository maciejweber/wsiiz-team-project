package com.example.myrzeszow_w;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Visibility;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView postsRV;
    private ImageView addBtn;

    private String url = "";

    private ArrayList<ModelPost> postArrayList;
    private AdapterPost adapterPost;

    private ProgressDialog progressDialog;

    private static final String TAG = "MAIN_TAG";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postsRV = findViewById(R.id.postsRV);
        addBtn = findViewById(R.id.addBtn);

        //progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");

        postArrayList = new ArrayList<>();
        postArrayList.clear();

        loadposts();
        String token = getIntent().getStringExtra("token");



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

    }

    public void loadposts() {
        progressDialog.show();

        url = "http://moj-rzeszow.herokuapp.com/api/posts/";
        Log.d(TAG, "loadposts: URL: " + url);

        // GET method

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response received, dismiss dialog
                progressDialog.dismiss();
                Log.d(TAG, "onResponse: " + response);

                // try/catch for json data
                try {
                    //get data
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String authorName = jsonObject1.getString("author");
                            String title = jsonObject1.getString("title");
                            String estate = jsonObject1.getString("estate");
                            String created_on = jsonObject1.getString("created_on");
                            String likes_count = jsonObject1.getString("likes_count");
                            String category = jsonObject1.getString("category");

                            //set data
                            ModelPost modelPost = new ModelPost(
                                    "" + authorName,
                                    "" + id,
                                    "" + created_on,
                                    "" + title,
                                    "" + estate,
                                    "" + category,
                                    "" + likes_count
                            );
                            //add data to list
                            postArrayList.add(modelPost);

                        } catch (Exception e) {
                            Log.d(TAG, "onResponse: 1" + e.getMessage());
                            Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    //adapter
                    adapterPost = new AdapterPost(MainActivity.this, postArrayList);
                    postsRV.setAdapter(adapterPost);
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: 2: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
                Toast.makeText(MainActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap header = new HashMap();
                String token = getIntent().getStringExtra("token");
//                header.put("Authorization", "Token "+token);
                //hardcoded for now, will be changed
                header.put("Authorization", "Token 06f41b40338b2817554825a93bf630a773c15451");
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}