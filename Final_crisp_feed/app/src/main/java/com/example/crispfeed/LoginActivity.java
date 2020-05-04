package com.example.crispfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener{

    private EditText email_edit_text, password_edit_text;
    private Button btn_submit;
    private FirebaseAuth mAuth;
    private ProgressDialog progressBar;

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent)
    {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)
        {
            SignUpCLicked(view);
        }
        return  false;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.relative_lay2)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void signup(View view)
    {
        Intent intent = new Intent(getApplicationContext(), EmailSignUp.class);
        startActivity(intent);
    }

    public void showCategoryPage(){
        Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
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
            Toast.makeText(LoginActivity.this,"Please enter email",Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_SHORT);
            return;
        }
        //progressBar.setMessage("Registration in progress");
        //progressBar.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //progressBar.dismiss();
                    Toast.makeText(LoginActivity.this,"User Signed Successfully",Toast.LENGTH_SHORT).show();
                    showCategoryPage();
                }
                else{
                    //progressBar.dismiss();
                    Toast.makeText(LoginActivity.this,"Login unsuccessfull "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        email_edit_text = findViewById(R.id.login_email);
        password_edit_text = findViewById(R.id.login_password);
        btn_submit = findViewById(R.id.login_sign_up);

        String email = email_edit_text.getText().toString().trim();
        String password = password_edit_text.getText().toString().trim();

        Log.d("emailll","signuppp"+email);

        mAuth.signInWithEmailAndPassword("abcmklmkm@gmail.com", "assword12").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("success e=s","sign in success");
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("success e=s","sign in fail");
                }
            }
        });


    }
}

