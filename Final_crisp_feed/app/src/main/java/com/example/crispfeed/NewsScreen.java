package com.example.crispfeed;

//WORK CITED FROM MOBILE COMPUTING CURRENCY_LAB_FALL2019 FROM BRIGHTSPACE
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//TODO code for News screen
public class NewsScreen extends AppCompatActivity {

    private Toolbar mToolBar;
    private Button shareButton;
    //private String News_Url;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String articleId;
    final Set<String> urlSet = new HashSet<>();
    private String title;
    private String description;
    private String urlToImage;
    private String url;
    private ArrayList<NewsItem> listNews;
    private Runnable runnable;
    private String newsUrl_intent;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_screen);

        mToolBar=findViewById(R.id.toolBar);
        setSupportActionBar(mToolBar);
        shareButton=findViewById(R.id.share_btn);
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            newsUrl_intent = extras.getString("newsUrl");
        }

        //News_Url = "https://newsapi.org/v2/top-headlines?country=ca&pageSize=100&sortBy=top&category=business&language=en&apiKey=3d9325bb60a94ecfb4ea1fe168a2bea7";


        mFirebaseInstance=FirebaseDatabase.getInstance();

        mFirebaseDatabase=mFirebaseInstance.getReference("ARTICLES");

        runnable=new Runnable() {
            @Override
            public void run() {
                getNews();
            }
        };
        Thread thread=new Thread(null,runnable,"background");
        thread.start();
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Your body here";
                String shareSub = "Sharing from Crisp Feed!!";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("add","DATA ADDED" + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(NewsScreen.this, CategoriesActivity.class));
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_icon:
                // User chose the "Settings" item, show the app settings UI...
                startActivity(new Intent(this, SettingsActivity.class));
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

    public void getNews(){

       final String newsUrl="https://newsapi.org/v2/top-headlines?country=ca&pageSize=100&sortBy=top&category=business&language=en&apiKey=3d9325bb60a94ecfb4ea1fe168a2bea7";

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, newsUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("articles");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject article=jsonArray.getJSONObject(i);
                        title = article.optString("title");
                        description=article.optString("description");
                        urlToImage=article.optString("urlToImage");
                        url = article.optString("url");
                        int upvote=0;

                        newNewsItem news =new newNewsItem(title,description,upvote,urlToImage,url);
                        DatabaseReference newsRef=mFirebaseDatabase.push();
                        newsRef.setValue(news);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestSingleTon.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}
