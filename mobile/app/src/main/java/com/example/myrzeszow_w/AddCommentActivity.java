package com.example.myrzeszow_w;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCommentActivity extends AppCompatActivity {

    private ImageView barImage;
    private TextView newCommentTextView;
    private TextView contentText;
    private TextView authorName;
    private String idposta;
    private EditText postCommentEdit;
    private Spinner authorSpinner;
    private AppCompatButton btnComment;

    private String postId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        //region init views
        barImage = findViewById(R.id.barImage);
        newCommentTextView = findViewById(R.id.newCommentTextView);
        contentText = findViewById(R.id.contentText);
        authorName = findViewById(R.id.authorName);
        postCommentEdit = findViewById(R.id.postCommentEdit);
        authorSpinner = findViewById(R.id.authorSpinner);
        btnComment = findViewById(R.id.btnComment);

        idposta = getIntent().getStringExtra("postId");
        //endregion

        //region populating spinners
        List<String> authors = Arrays.asList("ttest", "test",  "testowyuzytkownik", "maciej");
        ArrayAdapter<String> authorsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, authors);
        authorSpinner.setAdapter(authorsAdapter);
        //endregion


        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(postCommentEdit.getText().toString().equals("")){
                    Toast.makeText(AddCommentActivity.this, "Tresc komentarza wymagana", Toast.LENGTH_SHORT).show();
                }
                else{
                    addNewComment();
                    Intent intent = new Intent(AddCommentActivity.this, PostContentActivity.class);
                    intent.putExtra("postId", idposta);
                    startActivity(intent);
                }
            }
        });

    }

    private void addNewComment() {

        String text = postCommentEdit.getText().toString();
        long post = Integer.parseInt(idposta);
        String url = "http://moj-rzeszow.herokuapp.com/api/comments/";

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("text", text);
            jsonObject.put("post", post);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(AddCommentActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                Toast.makeText(AddCommentActivity.this, "Pomyslnie utworzono komentarz", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(AddCommentActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(AddCommentActivity.this, "Blad: Podaj poprawne dane", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Authorization","Token ca4d876643c57786168849699a9ac4f651d6832c");
                return hashMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddCommentActivity.this);
        requestQueue.add(objectRequest);

    }
}