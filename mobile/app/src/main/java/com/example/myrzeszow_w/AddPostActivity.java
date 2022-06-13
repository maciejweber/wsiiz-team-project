package com.example.myrzeszow_w;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        titleText = findViewById(R.id.contentText);
        postContentText = findViewById(R.id.postContentText);
        estateName = findViewById(R.id.authorName);
        categoryName = findViewById(R.id.categoryName);
        postTitleEdit = findViewById(R.id.postCommentEdit);
        postBodyEdit = findViewById(R.id.postBodyEdit);
        estateSpinner = findViewById(R.id.authorSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        btnPost = findViewById(R.id.btnComment);

        //endregion region

        //region populating spinners
        List<String> estates = Arrays.asList("Baranowka", "Drabinianka",  "Pobitno", "Stare Miasto", "Zalesie","Piastow");
        List<String> categories = Arrays.asList("Zmiany", "Eventy", "Sprzedam", "Kupie", "Praca","Ogolne");

        ArrayAdapter<String> estatesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estates);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        estateSpinner.setAdapter(estatesAdapter);
        categorySpinner.setAdapter(categoriesAdapter);
        //endregion

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(postTitleEdit.getText().toString().equals("") || postBodyEdit.getText().toString().equals("")){
                    Toast.makeText(AddPostActivity.this, "Tytul postu oraz tresc sa wymagane", Toast.LENGTH_SHORT).show();
                }
                else{
                    addNewPost();
                    //go back to main activity and load it again
                    Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
                    startActivity(intent);
                }
    }

    private void addNewPost() {

        String title = postTitleEdit.getText().toString();
        String body = postBodyEdit.getText().toString();
        long estate = estateSpinner.getSelectedItemId() + 1;
        long category = categorySpinner.getSelectedItemId() + 1;

        String url = "http://moj-rzeszow.herokuapp.com/api/posts/";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("estate", estate);
            jsonObject.put("category", category);
            jsonObject.put("content", body);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(AddPostActivity.this, "Pomyślnie utworzono nowego posta", Toast.LENGTH_SHORT).show();
                //Toast.makeText(AddPostActivity.this, "Response "+response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AddPostActivity.this, "Blad: Podaj poprawne dane", Toast.LENGTH_SHORT).show();
                //Toast.makeText(AddPostActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put("Context-Type", "application/json");
                hashMap.put("Authorization","Token 317ea0bde92de12d37799221b17a9959fd3df8b1");
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddPostActivity.this);
        requestQueue.add(objectRequest);
    }
        });
    }
}