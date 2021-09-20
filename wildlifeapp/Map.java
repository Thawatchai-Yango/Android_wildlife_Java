package com.example.birendra.wildlifeapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String parkName;
    Double lat, lang;
    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent rcv = getIntent();
        parkName = rcv.getStringExtra("park");
        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        lat = databaseAccess.getLat(parkName);
        lang = databaseAccess.getLang(parkName);
        databaseAccess.close();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.L
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.ACCESS_FINE_LOCATION"
            };
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, 200);
            }
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));

                MarkerOptions mp = new MarkerOptions();

                mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

                mp.title("My Location");

                mMap.addMarker(mp);
                mMap.moveCamera(center);


            }
        });
        databaseAccess.open();
        Bitmap image=databaseAccess.getImage1(parkName);
        Bitmap resizedImage=Bitmap.createScaledBitmap(image,150,150,false);
        BitmapDescriptor img=BitmapDescriptorFactory.fromBitmap(resizedImage);
        LatLng p=new LatLng(lat,lang);
        mMap.addMarker(new MarkerOptions().position(p).title(parkName).icon(img));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(5);
        mMap.animateCamera(zoom);
        mMap.setMapType(4);
}
}

