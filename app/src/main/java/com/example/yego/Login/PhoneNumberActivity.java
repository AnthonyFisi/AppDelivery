package com.example.yego.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.JwtResponse;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.ViewModel.UsuarioViewModel;

public class PhoneNumberActivity extends AppCompatActivity {



    private EditText textView_phone_number;

    private UsuarioViewModel viewModel;

    private CardView cardView_confirmar;

    private ProgressBar progressbar_phone;

    private TextView textView_message;

    private LinearLayout linearlayout_error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        textView_phone_number=findViewById(R.id.textView_phone_number);
        cardView_confirmar=findViewById(R.id.cardView_confirmar);
        progressbar_phone=findViewById(R.id.progressbar_phone);
        textView_message=findViewById(R.id.textView_message);
        linearlayout_error=findViewById(R.id.linearlayout_error);

        viewModel=new ViewModelProvider(this).get(UsuarioViewModel.class);
        viewModel.init();
        viewModel.getUsuarioLiveData().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                progressbar_phone.setVisibility(View.GONE);
                if(usuario!=null){

                    Cliente_Bi.getCliente().setCelular(usuario.getCelular());

                    Intent intent= LocationActivity.newIntentLocationActivity(PhoneNumberActivity.this,true);
                    startActivity(intent);
                    finish();

                }else {

                    textView_message.setVisibility(View.VISIBLE);
                    Toast.makeText(PhoneNumberActivity.this, "Intentarlo nuevamente", Toast.LENGTH_LONG).show();
                }
            }
        });


        cardView_confirmar.setOnClickListener(v->{


            if(textView_phone_number.getText().toString().length()==9 && String.valueOf(textView_phone_number.getText().charAt(0)).equals(String.valueOf(9))){
                progressbar_phone.setVisibility(View.VISIBLE);
                textView_message.setVisibility(View.GONE);
                viewModel.updateCelular(SessionPrefs.get(this).data().getId().intValue(),textView_phone_number.getText().toString());
            }else {
                linearlayout_error.setVisibility(View.VISIBLE);
            }
        });


    }
}
