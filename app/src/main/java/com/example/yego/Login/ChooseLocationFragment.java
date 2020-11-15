package com.example.yego.Login;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yego.R;
import com.example.yego.View.LocationUI.GoogleMaps.AgregarUbicacionActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseLocationFragment extends Fragment {


    private Button fragment_choose_location_MANUALLY,fragment_choose_location_AUTOMATICALLY;

    public ChooseLocationFragment() {
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
        return inflater.inflate(R.layout.fragment_choose_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWidget(view);
        buttonLocationAutomatically();
        buttonLocationManually();
    }

    private void initWidget(View view){
        fragment_choose_location_MANUALLY= view.findViewById(R.id.fragment_login_FACEBOOK);
        fragment_choose_location_AUTOMATICALLY=view.findViewById(R.id.fragment_login_GOOGLE);

    }

    private void buttonLocationManually(){
        fragment_choose_location_MANUALLY.setOnClickListener( v->{

            Intent intent= new Intent(getContext(), AgregarUbicacionActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }

    private void buttonLocationAutomatically(){
        fragment_choose_location_AUTOMATICALLY.setOnClickListener( v->{

        });
    }
}
