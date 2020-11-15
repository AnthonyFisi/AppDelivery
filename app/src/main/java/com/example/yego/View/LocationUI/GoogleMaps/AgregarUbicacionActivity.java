package com.example.yego.View.LocationUI.GoogleMaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.example.yego.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class AgregarUbicacionActivity extends AppCompatActivity  {

    private static final String INTENT_DATA="com.example.yego.Login";

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Geocoder geocoder;
    GoogleMap googleMap;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ubicacion);

        geocoder= new Geocoder(this);

        //Asign variable
        supportMapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        //Initiazlized fused locationn
        client= LocationServices.getFusedLocationProviderClient(this);

        //check permission
        if(ActivityCompat.checkSelfPermission(AgregarUbicacionActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //when permission grated
            //call method
            getCurrentLocation();

        }else{
            // when permission denied
            //Request permission

            ActivityCompat.requestPermissions(AgregarUbicacionActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }



    }


    private void getCurrentLocation(){
        //Initialize task location
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                //When succes

                if(location !=null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //Initialize lat lng
                            LatLng latLng= new LatLng(location.getLatitude(),location.getLongitude());
                            //create marker options
                            MarkerOptions options= new MarkerOptions().position(latLng)
                                    .title("I am here");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
                        // Add marker on map
                            googleMap.addMarker(options);

                            List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            }

                            System.out.println(" cantidad "+ addresses.get(0).getAddressLine(1));


                            for(Address a:addresses){
                                for(int i=0;i<a.getMaxAddressLineIndex();i++){
                                    System.out.println(" LINE INDEX "+a.getAddressLine(i));
                                }


                                System.out.println("LA DIRECCION ES LA SIGUIENTE : "+ a.getLocality() + " / "+a.getFeatureName()+" /  "+a.getCountryName()+ " / " +a.getPostalCode()  );
                                System.out.println(a.getUrl());
                            }


                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 44){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                //When permission grated
                // call methos
                getCurrentLocation();
            }
        }
    }



}


/**/
