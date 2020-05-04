package com.example.crispfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class EmailSignUp extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener{

    private EditText email_edit_text, password_edit_text;
    private Button btn_submit;
    private FirebaseAuth mAuth;
    private TextView textViewSignIn;
    private ProgressDialog progressBar;
    private RelativeLayout relativeLayout;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    LocationManager myManager;

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent)
    {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)
        {
            SignUpCLicked(view);
        }
        return  false;
    }

    public void showCategoryPage(){
        //Intent intent = new Intent(getApplicationContext(), Category_Screen.class);
        //startActivity(intent);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.relative_lay)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void signin(View view)
    {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void SignUpCLicked(View view)
    {
        Log.d("testing tag","new_tag");
        String email = email_edit_text.getText().toString().trim();
        String password = password_edit_text.getText().toString().trim();
        Log.d("my tag","aa"+email);
        Log.d("second tag","bb"+password);

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(EmailSignUp.this,"Please enter email",Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(EmailSignUp.this,"Please enter password",Toast.LENGTH_SHORT);
            return;
        }
        progressBar.setMessage("Registration in progress");
        progressBar.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(EmailSignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressBar.dismiss();
                    Toast.makeText(EmailSignUp.this,"User Registered Successfully",Toast.LENGTH_SHORT).show();
                    showCategoryPage();
                }
                else{
                    progressBar.dismiss();
                    Toast.makeText(EmailSignUp.this,"Registration Unsuccessfull: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressBar = new ProgressDialog(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sign_up);
        email_edit_text = findViewById(R.id.email);
        password_edit_text = findViewById(R.id.password);
        btn_submit = findViewById(R.id.sign_up_button);
        relativeLayout = findViewById(R.id.relative_lay);
        relativeLayout.setOnClickListener(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        myManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener(){
            @Override
            public void onLocationChanged(Location location) {
                Log.i("new location",location.toString());
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> listaddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (listaddress != null && listaddress.size() > 0)
                    {
                        Log.i("place info", listaddress.get(0).toString());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        myManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10,
                10, locationListener);




    }



}
