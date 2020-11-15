package com.example.yego.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.yego.R;

public class CarritoGeneralActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_general);
    }

    public static Intent newIntentCarritoActivity2(Context packageContext){
        Intent intent =new Intent(packageContext,CarritoGeneralActivty.class);
        return intent;
    }

}
