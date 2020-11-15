package com.example.yego.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.yego.R;

public class LocationActivity extends AppCompatActivity {

    private Button activity_location_MANUALLY_GPS;
    private CardView activity_location_AUTOMATICALLY_GPS;

    private static final String TYPE_ROUTE = "typeRoute";

    private boolean route=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
     //   reciveDataIntent();
        reciveData();
        initWidget();
        buttonLocationAutomatically();
        buttonLocationManually();

    }

    private void initWidget(){
        activity_location_AUTOMATICALLY_GPS= findViewById(R.id.activity_location_AUTOMATICALLY_GPS);
        activity_location_MANUALLY_GPS=findViewById(R.id.activity_location_MANUALLY_GPS);

    }




    private void buttonLocationManually(){
        activity_location_MANUALLY_GPS.setOnClickListener( v->{
            Intent intent= GoogleMapsOficial.newIntentGoogleMaps(getApplicationContext(),false,route);
            startActivity(intent);
            finish();
        });
    }

    private void buttonLocationAutomatically(){
        activity_location_AUTOMATICALLY_GPS.setOnClickListener( v->{
            Intent intent= GoogleMapsOficial.newIntentGoogleMaps(getApplicationContext(),true,route);
            startActivity(intent);
            finish();
        });
    }


    public static Intent newIntentLocationActivity(Context context,boolean route){
        Intent intent= new Intent(context, LocationActivity.class);
        intent.putExtra(TYPE_ROUTE,route);
        return intent;
    }


    private  void reciveData(){
        route=getIntent().getBooleanExtra(TYPE_ROUTE,false);
    }


}
