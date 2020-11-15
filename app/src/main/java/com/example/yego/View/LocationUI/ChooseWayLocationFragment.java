package com.example.yego.View.LocationUI;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yego.Login.GoogleMapsOficial;
import com.example.yego.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseWayLocationFragment extends BottomSheetDialogFragment {

    public static final String TAG = "tagChoose";
    private Button activity_location_MANUALLY_GPS;
    private CardView activity_location_AUTOMATICALLY_GPS;


    public ChooseWayLocationFragment() {
        // Required empty public constructor

    }


    public static  ChooseWayLocationFragment newInstance() {
        return new ChooseWayLocationFragment();
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_way_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWidget(view);
        buttonLocationAutomatically();
        buttonLocationManually();
    }


    private void initWidget(View view){
        activity_location_AUTOMATICALLY_GPS= view.findViewById(R.id.activity_location_AUTOMATICALLY_GPS);
        activity_location_MANUALLY_GPS=view.findViewById(R.id.activity_location_MANUALLY_GPS);

    }




    private void buttonLocationManually(){
        activity_location_MANUALLY_GPS.setOnClickListener( v->{
            Intent intent= GoogleMapsOficial.newIntentGoogleMaps(getApplicationContext(),false,false);
            startActivity(intent);
        });
    }

    private void buttonLocationAutomatically(){
        activity_location_AUTOMATICALLY_GPS.setOnClickListener( v->{
            Intent intent= GoogleMapsOficial.newIntentGoogleMaps(getApplicationContext(),true,false);
            startActivity(intent);
        });
    }



}
