package com.example.yego.View.CarritoUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.IntentCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.View.HomeActivity;

import org.w3c.dom.Text;

public class ResultadoRegistroActivity extends AppCompatActivity {

    public static final String PASS_DATA_NOMBRE_UBICACION="nombreUbicacion";

    public static final String PASS_DATA_DETALLE_UBICACION="detalleUbicacion";


    private TextView activity_resultado_registro_NOMBRE_USUARIO,activity_resultado_registro_NOMBRE_UBICACION,activity_resultado_registro_NOMBRE_DETALLE_UBICACION;
    private ImageButton activity_resultado_registro_GO_CLOSE;
    private Button activity_resultado_registro_GO_HOME;

    private String nombreUbicacion;
    private String detalleUbicacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_registro);

        //GET DATA INTENT
        intentData();

        //DELCRAR WODGET
        declararWidgets();

        //SET DATA WIDGET
        setDataWidget();

        //CLOSE ACTIVITY
        closeActivity();

    }

    public static Intent newIntentResultadoActivity(Context context , String nombreUbicacion, String detalleUbicacion){
        Intent intent= new Intent(context,ResultadoRegistroActivity.class);
        intent.putExtra(PASS_DATA_NOMBRE_UBICACION,nombreUbicacion);
        intent.putExtra(PASS_DATA_DETALLE_UBICACION,detalleUbicacion);
        return intent;
    }

    private void intentData(){

        nombreUbicacion= getIntent().getStringExtra(PASS_DATA_NOMBRE_UBICACION);
        detalleUbicacion= getIntent().getStringExtra(PASS_DATA_DETALLE_UBICACION);
    }

    public void declararWidgets(){

        activity_resultado_registro_NOMBRE_USUARIO=findViewById(R.id.activity_resultado_registro_NOMBRE_USUARIO);
        activity_resultado_registro_NOMBRE_UBICACION=findViewById(R.id.activity_resultado_registro_NOMBRE_UBICACION);
        activity_resultado_registro_NOMBRE_DETALLE_UBICACION=findViewById(R.id.activity_resultado_registro_NOMBRE_DETALLE_UBICACION);
        activity_resultado_registro_GO_CLOSE=findViewById(R.id.activity_resultado_registro_GO_CLOSE);
        activity_resultado_registro_GO_HOME=findViewById(R.id.activity_resultado_registro_GO_HOME);
    }

    private void setDataWidget(){
        activity_resultado_registro_NOMBRE_USUARIO.setText(Cliente_Bi.getCliente().getNombre() +" ,Cuentas con nueva ubicacion" );
        activity_resultado_registro_NOMBRE_UBICACION.setText(nombreUbicacion);
        activity_resultado_registro_NOMBRE_DETALLE_UBICACION.setText(detalleUbicacion);
    }

    private void closeActivity(){
        activity_resultado_registro_GO_CLOSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        activity_resultado_registro_GO_HOME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean answer=false;

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
            answer=true;
        }
        return answer;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent intent= new Intent(ResultadoRegistroActivity.this,HomeActivity.class);
        //startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        ActivityCompat.finishAffinity(this);
    }
}
