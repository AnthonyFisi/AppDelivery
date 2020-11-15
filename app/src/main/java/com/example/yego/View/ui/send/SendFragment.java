package com.example.yego.View.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.yego.Login.IniciarSesionActivity;
import com.example.yego.Login.SessionPrefs;
import com.example.yego.R;
import com.example.yego.View.InicioActivity;

public class SendFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_send, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cerrarSersion();
    }

    private void cerrarSersion(){

            Handler handler = new Handler();

            final Runnable r = () -> {
                //tv.append("Hello World");

                SessionPrefs.get(getContext()).logOut();
                Intent intent= new Intent(getContext(), IniciarSesionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


                //handler.postDelayed(this, 1000);
            };

            handler.postDelayed(r, 4000);



    }
}