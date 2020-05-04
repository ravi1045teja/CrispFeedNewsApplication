package com.example.crispfeed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class SettingsActivity extends AppCompatActivity {
    Button button;
    FirebaseAuth mAuth;
    private String user_name;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;
    private TextView usern;
    private ImageView img_holder;
    private Button btn_help;



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        btn_help=findViewById(R.id.help_btn);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "group5.2019.mc@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Send Feedback or ask for help");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Feedback or help"));
            }
        });
        usern = findViewById(R.id.user_name);
        img_holder = findViewById(R.id.imageView);
        mAuth= FirebaseAuth.getInstance();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Log.d("current user","userr"+mAuth.getCurrentUser());

        try{
            if (mAuth.getCurrentUser().equals(null)) {
                Log.d("no user", "no user logged in");

            } else {
                user = mAuth.getCurrentUser();
                user_name=user.getEmail().substring(0,user.getEmail().lastIndexOf("@"));
                usern.setText(user_name);
                Log.d("user name","user name"+user);
            }
            if(user.getDisplayName().equals(null)||user.getDisplayName().equals("null") ){

                Log.d("user name","user name");

            }
            else{
                user_name=user.getDisplayName();
                usern.setText(user_name);

            }
            if(!user.getPhotoUrl().equals(null)){
                Log.d("image link","http link"+user.getPhotoUrl());
                Picasso.get().load(user.getPhotoUrl()).into(img_holder);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        button=findViewById(R.id.sign_out_btn);


        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(SettingsActivity.this,MainActivity.class));
                    //TODO while designing in settings page, redirect into settings activity itself
                }
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}