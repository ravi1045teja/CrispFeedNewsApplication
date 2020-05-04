package com.example.crispfeed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HelperFirebase {

    FirebaseDatabase datab;
    DatabaseReference db;
    ArrayList<NewsItem> news = new ArrayList<>();


    // constructor
    public HelperFirebase(DatabaseReference db) {
        this.db = db;
        try {
            datab = FirebaseDatabase.getInstance();
            db = datab.getReference("crispfeed-1d968");
            db.setValue("Hello vviill");
//
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    // function for fetching news items one by one and loading in the array list
    private void fetchData(DataSnapshot dataSnapshot) {
        news.clear();
        for(DataSnapshot ds: dataSnapshot.getChildren()) {
            NewsItem single_news = ds.getValue(NewsItem.class);
            news.add(single_news);
        }
    }


    // retrieving from the database
    public ArrayList<NewsItem> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged( DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved( DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved( DataSnapshot dataSnapshot,  String s) {

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
        return news;
    }


}
