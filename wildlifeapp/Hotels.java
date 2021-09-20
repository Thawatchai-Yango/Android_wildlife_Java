package com.example.birendra.wildlifeapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Hotels extends AppCompatActivity {
    DatabaseAccess databaseAccess;
    String hotel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        Intent caller = getIntent();
        hotel = caller.getStringExtra("hotel");
        TextView hotelDesc=(TextView)findViewById(R.id.textViewHotelDescription);
        databaseAccess.open();
        hotelDesc.setText(databaseAccess.getHotelDesc(hotel));
    }
    public void setHotelTicket(View view)
    {
        databaseAccess.open();
        String url=databaseAccess.getURLHotel(hotel);
        Intent viewIntent=new Intent("android.intent.action.VIEW",Uri.parse(url));
        startActivity(viewIntent,ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),android.R.anim.fade_in,android.R.anim.fade_out).toBundle());
    }
}
