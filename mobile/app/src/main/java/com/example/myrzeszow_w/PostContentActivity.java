package com.example.myrzeszow_w;

import static javax.xml.transform.OutputKeys.ENCODING;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//TODO: odwrocic kolejnosc postow
//TODO: zmienic kategorie na text zamiast id
//TODO: dodac content do POST request
//TODO: dodac search GET request
//TODO: zmienic author w "comments" na String zamiast ID


public class PostContentActivity extends AppCompatActivity {

    private TextView titleTV, publishedInfo, regionTV, categoryTV;
    private WebView webView;
    private RecyclerView commentsRV;

    private String postId; // passed in intent from AdapterPost
//    private String header;
    private static final String TAG = "POST_CONTENT";
    private static final String TAG_COMMENTS = "POST_COMMENTS";

    private ActionBar actionBar;

    private ArrayList<ModelComment> commentArrayList;
    private AdapterComment adapterComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_content);

        //init actionbar
//        actionBar = getSupportActionBar();
////        actionBar.setTitle("Tytul postu");
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        //init views
        titleTV = findViewById(R.id.contentTitle);
        publishedInfo = findViewById(R.id.contentPublished);
        regionTV = findViewById(R.id.contentEstate);
        categoryTV = findViewById(R.id.contentCategory);
        commentsRV = findViewById(R.id.commentsRV);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        commentsRV.setLayoutManager(manager);
//        commentsRV.setHasFixedSize(true);
//        adapter = new MyAdapter();
//        recyclerView.setAdapter(adapter);
        webView = findViewById(R.id.contentWebView);
        webView.setBackgroundColor(Color.parseColor("#e0e0e0"));
        
        postId = getIntent().getStringExtra("postId");
//        header = getIntent().getStringExtra("Authorization");
        Log.d(TAG, "onCreate: postid:"+postId);//+"header: "+header);
        
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        loadPostContent();

    }

    private void loadPostContent() {

        String url = "http://moj-rzeszow.herokuapp.com/api/posts/"+postId;

        Log.d(TAG, "loadPostContent: URL" +url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);

                try{
                    //get data
                    JSONObject jsonObject = new JSONObject(response);

                    String title = jsonObject.getString("title");
                    String id = jsonObject.getString("id");
                    String estate = jsonObject.getString("estate");
                    String authorName = jsonObject.getString("author");
                    String content = jsonObject.getString("content");
                    String category = jsonObject.getString("category");
                    //String created_on = jsonObject.getString("created_on");

                    //region date conversion

//                    String gmtDate = created_on;
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //   2022-06-06T06:53:00-07:00
//                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy K:mm a"); // 06/06/2022 15:53
//                    String formattedDate = "";
//                    try {
//                        Date date = dateFormat.parse(gmtDate);
//                        formattedDate = dateFormat2.format(date);
//
//
//                    } catch (Exception e) {
//                        formattedDate = created_on;
//                        e.printStackTrace();
//                    }

                    //endregion

                    //set data
                    //actionBar.setSubtitle(title);
                    titleTV.setText(title);
                    publishedInfo.setText("Autor Postu: " + authorName);
                    regionTV.setText(estate);
                    categoryTV.setText(category);
                    webView.loadDataWithBaseURL(null,content,"text/html", ENCODING, null);

                    loadComments();

                }catch (Exception e){
                    Log.d(TAG, "onResponse: "+e.getMessage());
                    Toast.makeText(PostContentActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostContentActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put("Context-Type", "application/json");
                hashMap.put("Authorization","Token ca4d876643c57786168849699a9ac4f651d6832c");
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadComments(){
        String url = "http://moj-rzeszow.herokuapp.com/api/posts/"+postId;
        Log.d(TAG_COMMENTS, "loadComments: URL: "+url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG_COMMENTS, "onResponse: "+response);

                commentArrayList = new ArrayList<>();
                commentArrayList.clear();

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("comments");

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObjectComment = jsonArray.getJSONObject(i);
                        String id = jsonObjectComment.getString("id");
                        String text = jsonObjectComment.getString("text");
                        String created_on = jsonObjectComment.getString("created_on");
                        String author = jsonObjectComment.getString("author");

                        ModelComment modelComment = new ModelComment(
                                ""+id,
                                ""+text,
                                ""+created_on,
                                ""+author
                        );

                        commentArrayList.add(modelComment);

                        }

                    adapterComment = new AdapterComment(PostContentActivity.this, commentArrayList);
                    commentsRV.setAdapter(adapterComment);


                }catch (Exception e){
                    Log.d(TAG_COMMENTS, "onResponse: "+e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG_COMMENTS, "onErrorResponse: "+error.getMessage());
                Toast.makeText(PostContentActivity.this, "No response: "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put("Context-Type", "application/json");
                hashMap.put("Authorization","Token ca4d876643c57786168849699a9ac4f651d6832c");
                return hashMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}