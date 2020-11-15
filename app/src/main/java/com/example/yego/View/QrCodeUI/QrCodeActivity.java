package com.example.yego.View.QrCodeUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.View.EmpresaDetailActivity;
import com.example.yego.ViewModel.EmpresaViewModel;
import com.google.zxing.Result;

import static android.Manifest.permission.CAMERA;

public class QrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "QrCodeActivity";
    private ZXingScannerView view_qr_code;
    private LinearLayout loading_buscando_negocio,resultado_failed,permiso_denegado;
    private CardView back_inicio;
    private boolean resouesta=false;
    private Button solicitar_permiso;

    private EmpresaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        declararWidgets();
        viewModel= new ViewModelProvider(this).get(EmpresaViewModel.class);
        viewModel.init();

        loadData();

        clickBackToInicio();

        if(validarPermiso()){
            view_qr_code.setResultHandler(QrCodeActivity.this); // Register ourselves as a handler for scan results.
            view_qr_code.startCamera();
        }else {
            permiso_denegado.setVisibility(View.VISIBLE);
        }

        solicitar_permiso.setOnClickListener(v->{
            cargarDialogoRecomendacion();
        });


    }

    private boolean validarPermiso() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{CAMERA},100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                //botonCargar.setEnabled(true);
                view_qr_code.setResultHandler(QrCodeActivity.this); // Register ourselves as a handler for scan results.
                view_qr_code.startCamera();

            }else{
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(QrCodeActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(QrCodeActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{CAMERA},100);
                }
            }
        });
        dialogo.show();
    }



    private void declararWidgets(){

        view_qr_code=findViewById(R.id.view_qr_code);
        permiso_denegado=findViewById(R.id.permiso_denegado);
        solicitar_permiso=findViewById(R.id.solicitar_permiso);
/*
        view_qr_code.
                setAspectTolerance ( 0.5f );*/

        //setContentView(view_qr_code);
        loading_buscando_negocio=findViewById(R.id.loading_buscando_negocio);
        resultado_failed=findViewById(R.id.resultado_failed);
        back_inicio=findViewById(R.id.back_inicio);



    }

    @Override
    public void onResume() {
        super.onResume();
           // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        view_qr_code.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        if(!resouesta){
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        view_qr_code.resumeCameraPreview(this);
        view_qr_code.setVisibility(View.GONE);
        loading_buscando_negocio.setVisibility(View.VISIBLE);

        int idEmpresa=Integer.valueOf(rawResult.getText().toString().trim());


            viewModel.getEmpresaById(idEmpresa);
            resouesta=true;
        }

    }


    private void loadData(){
        viewModel.getEmpresaLiveData().observe(this, empresa -> {
            loading_buscando_negocio.setVisibility(View.GONE);
            if(empresa!=null){

                Intent intent= EmpresaDetailActivity.newIntentEmpresa(QrCodeActivity.this,empresa);
                startActivity(intent);
                finish();

            }else{

                resultado_failed.setVisibility(View.VISIBLE);
            }
        });
    }

    private void clickBackToInicio(){
        back_inicio.setOnClickListener(v->{
            finish();
        });
    }


}
