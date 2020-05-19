package com.example.yego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yego.View.HomeActivity;

public class MainActivity extends AppCompatActivity {
    private Button  mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mButton =(Button) findViewById(R.id.button_login);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent (view.getContext(), HomeActivity.class);
                startActivityForResult(intent2, 0);
            }
        });




    }
}
