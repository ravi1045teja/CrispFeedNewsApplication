package com.example.crispfeed;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewsList extends AppCompatActivity {

    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    ArrayList<NewsItem> newsList = new ArrayList<>();
    NewsAdapter adapter;
    private Runnable runnable;
    private Button btn_share;
    private String category;
    private String  loc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);


        myRef = database.getReference().child("ARTICLES");
        ListView lvNews = findViewById(R.id.lvNews);
        adapter = new NewsAdapter(this, R.layout.list_view_news, newsList);
        lvNews.setAdapter(adapter);

        runnable = new Runnable() {
            @Override
            public void run() {
                ret();
            }
        };

        Thread thread = new Thread(null, runnable, "Bg");
        thread.start();




    }






    public void showdata(DataSnapshot dataSnapshot)
    {
        for(DataSnapshot ds: dataSnapshot.getChildren()) {
            NewsItem singleNews = new NewsItem();
            String res = ds.getKey();

            Log.d("upvote count", "upvote vidip count"+ds.child("location").getValue(String.class));
            if (ds.child("location").getValue(String.class) != null) {
                if(ds.child("location").getValue(String.class).equals(loc)) {
                    Log.d("result of key", "keyss" + res);
                    newsList.add(new NewsItem(ds.child("title").getValue(String.class), ds.child("description").getValue(String.class), Integer.parseInt(ds.child("upvote").getValue().toString()), ds.child("urlToImage").getValue(String.class), ds.child("url").getValue(String.class), ds.getKey()));
                }
            }
            Log.d("final result print","vidip"+ds.child("description").getValue(String.class));
        }
        adapter.notifyDataSetChanged();
    }


    public void ret() {
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            category = extras.getString("newsvalue");
            loc=extras.getString("loc");
        }
        Log.d("news value","news value"+category);
        Query query = myRef.orderByChild("category").equalTo(category);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("into data change", "data change mechanism");
                // This method is called once with the initial value and again
                // whenever data at this location is updated
                newsList.clear();
                String value = dataSnapshot.getKey();
//                String value = dataSnapshot.getValue(String.class);
                showdata(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("gvgv", "Failed to read value.", error.toException());
            }
        });


    }
}


//
//    public void fetchData(DataSnapshot dataSnapshot) {
//        newsList.clear();
//        for(DataSnapshot ds: dataSnapshot.getChildren()) {
//            NewsItem single_news = ds.getValue(NewsItem.class);
//            newsList.add(single_news);
//        }
//    }
//
//    public ArrayList<NewsItem> retrieve() {
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                fetchData(dataSnapshot);
//            }
//
//            @Override
//            public void onChildChanged( DataSnapshot dataSnapshot, String s) {
//                fetchData(dataSnapshot);
//            }
//
//            @Override
//            public void onChildRemoved( DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved( DataSnapshot dataSnapshot,  String s) {
//
//            }
//
//            @Override
//            public void onCancelled( DatabaseError databaseError) {
//
//            }
//        });
//        return newsList;
//    }
//}
