package com.example.crispfeed;
//Work cited from https://firebase.google.com/docs/auth/android/google-signin
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    NewsList newlist;

    private ArrayList<NewsItem> newsList;
    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    public NewsAdapter(Context context, int textViewResourceID, ArrayList<NewsItem> newsList) {
        super(context, textViewResourceID, newsList);
        this.newsList = newsList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;


        if(v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_view_news, null);
        }

        final NewsItem i = newsList.get(position);

        if(i != null) {
            TextView headLine = v.findViewById(R.id.headline);
            TextView article = v.findViewById(R.id.newsStory);
            TextView upvote = v.findViewById(R.id.hyperlink2);
            ImageView img_news = v.findViewById(R.id.img_news);
            Button btn_share = v.findViewById(R.id.btn_share);
            Button btn_upvote = v.findViewById(R.id.btn_upvote);
            TextView tv = v.findViewById(R.id.hyperlink);


            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = i.getHeadline();
                    Log.d("aalo baingan", "daa"+shareBody);
                    String shareSub = "Sharing from Crisp Feed!!";
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    getContext().startActivity(Intent.createChooser(sharingIntent, "Share using"));
                }
            });

            headLine.setText(i.getHeadline());
            article.setText(i.getDescription());
            upvote.setText(String.valueOf(i.getUpvote()));
            Picasso.get().load(i.getUrlToImage()).into(img_news);

            btn_upvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("final string value", "value"+i.getId());
                    String id = i.getId();
                    Log.d("upvote button clicked", "upvite clicked");
                    myRef = database.getReference().child("ARTICLES");
                    myRef.child(id).child("upvote").setValue(i.getUpvote()+1);
                    //newlist.showdata();


                }
            });

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(i.getUrl()));
                    getContext().startActivity(browserIntent);
                }
            });


        }

        return v;
    }
}