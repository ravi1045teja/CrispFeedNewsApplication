package com.example.crispfeed;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class CategoriesActivity extends AppCompatActivity {

    private CardView Business,Sports,Science,Technology;
    private String nn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("locations3",MODE_PRIVATE,null);
        Cursor cu = myDatabase.rawQuery("SELECT * FROM locations3",null);
        int nameIndex = cu.getColumnIndex("location_name");
        cu.moveToFirst();
        Log.i("name","vidipmal"+cu.moveToLast());
        String locality = cu.getString(nameIndex);
        while(!cu.isAfterLast())
        {
            Log.i("name","vidipmal"+cu.getString(nameIndex));
            nn = cu.getString(nameIndex);
            Log.i("nn","vidipguneeet"+nn);
            cu.moveToNext();
        }
        Log.d("locaties", "locality"+locality);
        setContentView(R.layout.activity_categories);

        Toolbar cat_tool=findViewById(R.id.toolbar_cat);
        setSupportActionBar(cat_tool);



        Business = (CardView) findViewById(R.id.categorybusiness);
        Sports=(CardView) findViewById(R.id.categorysports);
        Science=(CardView) findViewById(R.id.categoryscience);
        Technology=(CardView) findViewById(R.id.categorytechnology);

        Business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CategoriesActivity.this, NewsList.class);
                intent.putExtra("newsvalue", "business");
                intent.putExtra("loc",nn);
                startActivity(intent);
            }
        });

        Sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CategoriesActivity.this, NewsList.class);
                intent.putExtra("newsvalue", "sports");
                startActivity(intent);
            }
        });

        Science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(CategoriesActivity.this, NewsList.class);
                intent.putExtra("newsvalue", "science");
                startActivity(intent);
            }
        });

        Technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CategoriesActivity.this, NewsList.class);
                intent.putExtra("newsvalue", "business");
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CategoriesActivity.this);
        builder.setMessage("Are you sure want to close this?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Close!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category_menu, menu);
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




}
