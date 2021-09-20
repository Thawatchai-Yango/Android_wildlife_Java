package com.example.birendra.wildlifeapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomePage extends AppCompatActivity {
    ArrayAdapter<String> dataAdapter1;
    ArrayAdapter<String> dataAdapter2;
    Spinner spinner2,spinner1;
    Intent goToPark;
    SearchView srcv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {
                    "android.permission.ACCESS_NETWORK_STATE",
                    "android.permission.INTERNET"
            };
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, 200);
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.ACCESS_FINE_LOCATION"
            };
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, 200);
            }
        }
        createSpin1();
        createSpin2();

       spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0)
                {

                    goToPark=new Intent().setClass(getApplicationContext(), ListOfParks.class);
                    String selected=adapterView.getItemAtPosition(i).toString();
                    goToPark.putExtra("state",selected);
                    startActivity(goToPark,ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),android.R.anim.fade_in,android.R.anim.fade_out).toBundle());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0)
                {

                    goToPark=new Intent().setClass(getApplicationContext(), Information.class);
                    String selected2=adapterView.getItemAtPosition(i).toString();
                    goToPark.putExtra("park",selected2);
                    startActivity(goToPark,ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),android.R.anim.fade_in,android.R.anim.fade_out).toBundle());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SearchView srcv=(SearchView)findViewById(R.id.searchMenuAnimal);
        srcv.setMaxWidth(Integer.MAX_VALUE);
        srcv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                goToPark=new Intent().setClass(getApplicationContext(), ListOfParks.class);
                goToPark.putExtra("animal",s.trim());
                startActivity(goToPark,ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),android.R.anim.fade_in,android.R.anim.fade_out).toBundle());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
    }
    public void createSpin1()
    {
        spinner1 = (Spinner) findViewById(R.id.spinnerStates);
        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        ArrayList<String> states = new ArrayList<String>();
        states.add("Uttarakhand");
        states.add("West Bengal");
        states.add("Rajasthan");
        states.add("Gujarat");
        states.add("Assam");
        states.add("Madhya Pradesh");
        states.add("Haryana");
        states.add("Kerala");
        Collections.sort(states);
        states.add(0,"Select by State");
        dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, states);
        spinner1.setAdapter(dataAdapter1);

    }
    public void createSpin2()
    {
        spinner2 = (Spinner) findViewById(R.id.spinnerParks);
        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        ArrayList<String> parks= databaseAccess.getParks();
        Collections.sort(parks);
        parks.add(0,"Select by Wildlife Sanctuary");
        dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, parks);
        spinner2.setAdapter(dataAdapter2);
    }

}
