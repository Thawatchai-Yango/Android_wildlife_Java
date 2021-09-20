package com.example.birendra.wildlifeapp;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SetHotels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_hotels);
        Intent rcv=getIntent();
        String park=rcv.getStringExtra("park");
        ListView hotelList=(ListView)findViewById(R.id.listHotels);
        ArrayList <String> hotels=new ArrayList<String>();
        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        hotels=databaseAccess.getHotelList(park);
        ArrayAdapter <String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,hotels);
        hotelList.setAdapter(arrayAdapter);
        hotelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent goToHotels = new Intent().setClass(getApplicationContext(), Hotels.class);
                String str = adapterView.getItemAtPosition(i).toString();
                goToHotels.putExtra("hotel", str);
                startActivity(goToHotels,ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),android.R.anim.fade_in,android.R.anim.fade_out).toBundle());
            }
        });
    }
}
