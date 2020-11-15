package com.example.yego.View.LocationUI.GoogleMaps;

import android.app.Activity;
import android.content.Context;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yego.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;


public class FragmentMapsAndSearchLocation extends Fragment implements OnMapReadyCallback {

    GoogleMap gMap;
    Context mContext;


    public FragmentMapsAndSearchLocation() {
    }


    public static FragmentMapsAndSearchLocation newInstance(String param1, String param2) {
        FragmentMapsAndSearchLocation fragment = new FragmentMapsAndSearchLocation();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_maps_and_search_location, container, false);

        SupportMapFragment supportMapFragment=(SupportMapFragment)
                requireActivity().getSupportFragmentManager().findFragmentById(R.id.google_map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap=googleMap;
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                MarkerOptions markerOptions=new MarkerOptions();

                markerOptions.position(latLng);

                markerOptions.title(latLng.latitude+ " : "+latLng.longitude);

                gMap.clear();

                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                gMap.addMarker(markerOptions);

            }
        });

    }
}
