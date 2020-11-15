package com.example.yego.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Gson.GsonUbicacion;
import com.example.yego.Repository.Modelo.JwtResponse;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.View.InicioActivity;
import com.example.yego.View.LocationUI.DetailsUbicacionActivity;
import com.example.yego.ViewModel.UbicacionViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.example.yego.R.*;

public class GoogleMapsOficial extends AppCompatActivity implements OnMapReadyCallback {

    private static final String SHOW_GPS = "shoGps" ;
    private static final String TYPE_ROUTE = "typeRoute";
    private EditText ediText,direccion_GOOGLE_MAPS;
    private CardView agregar_ubicacion;
    private String placeAddress,placeName,placeDistrito,placeDetalle;
    private String calle,distrito,pais;
    private double placeLatitud;
    private double placeLongitud;
    private String ubitotal;
    private GoogleMap gMap;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    private boolean showGps=false;
    private boolean route=false;

    private CardView cardView_show_gps;

    private LinearLayout linearlayout_direccion;

    private TextView text_error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_google_maps);


        reciveData();


        //Asign variable
        supportMapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(id.google_map);
        supportMapFragment.getMapAsync(this);

        //Initiazlized fused locationn
        client= LocationServices.getFusedLocationProviderClient(this);


        //DECLARAR TODOS LOS WIDGETS QUE SE VAN USAR
        declararWidgets();


        //BUSCAR POR LA API DE GOOGLE LUGAR EN DONDE TE ENCUENTRAS AHORA
        searchLocationAutoComplete();


        //MOSTRAR AUTOMATICAMENTE LA POSICION DE LA PERSONA
        showLocationAutomatically();



        //REGISTRAR UBICACION
        registrarUbicacion();

        listener();

        clickListener();

        cardView_show_gps.setVisibility(View.GONE);



    }

    private boolean validarPais(String placeAddress) {
        return pais.trim().equals("Peru") || pais.trim().equals("PERU") || pais.trim().equals("Perú");
    }




    private void declararWidgets(){
        ediText= findViewById(id.edit_text);
        direccion_GOOGLE_MAPS=findViewById(id.direccion_GOOGLE_MAPS);
        agregar_ubicacion=findViewById(id.agregar_ubicacion);
        direccion_GOOGLE_MAPS.setCursorVisible(false);
        direccion_GOOGLE_MAPS.setEnabled(false);
        linearlayout_direccion=findViewById(id.linearlayout_direccion);
        text_error=findViewById(id.text_error);
        cardView_show_gps=findViewById(id.cardView_show_gps);

    }

    private void searchLocationAutoComplete(){


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(string.googlemaps_place_apikey));
        }else{
            System.out.println("failed");
        }

        ediText.setFocusable(false);

        ediText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME)    ;

                Intent intent= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(GoogleMapsOficial.this);

                startActivityForResult(intent,100);

            }
        });

    }

    private void listener(){
        direccion_GOOGLE_MAPS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()>0){
                    linearlayout_direccion.setBackground(getResources().getDrawable(drawable.border_editext_able));
                    text_error.setVisibility(View.GONE);
                }else {
                    linearlayout_direccion.setBackground(getResources().getDrawable(drawable.border_editext_enable));
                }
            }
        });
    }

    private void clickListener(){
        linearlayout_direccion.setOnClickListener(v->{
            linearlayout_direccion.setBackground(getResources().getDrawable(drawable.border_editext_able));
        });

        direccion_GOOGLE_MAPS.setOnClickListener(v->{
            linearlayout_direccion.setBackground(getResources().getDrawable(drawable.border_editext_able));

        });
    }

    private void showLocationAutomatically(){

        if(showGps){

            //check permission
            if(ActivityCompat.checkSelfPermission(GoogleMapsOficial.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                //when permission grated
                //call method
                getCurrentLocation();

            }else{
                // when permission denied
                //Request permission

                ActivityCompat.requestPermissions(GoogleMapsOficial.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            }

        }else{

            ediText.setFocusable(true);
            ediText.setCursorVisible(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(ediText, InputMethodManager.SHOW_FORCED);


            List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME)    ;

            Intent intent= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(GoogleMapsOficial.this);

            startActivityForResult(intent,100);

        }
    }


    private void registrarUbicacion(){

        agregar_ubicacion.setOnClickListener(view -> {



            if(direccion_GOOGLE_MAPS.getText().toString().length() > 5 ){

                if(validarPais(placeAddress)){
                    Ubicacion ubicacion= new Ubicacion(
                            0,
                            placeAddress,
                            "",
                            SessionPrefs.get(getApplicationContext()).data().getId().intValue(),
                            true,
                            false,
                            placeDistrito,
                            placeDetalle,
                            String.valueOf(placeLatitud),
                            String.valueOf(placeLongitud)
                    );

                    Intent intent= DetailsUbicacionActivity.newIntentGoogleMaps(GoogleMapsOficial.this,ubicacion,route);
                    startActivity(intent);
                }else {
                    Toast.makeText(this,"Este lugar no esta habiliatado",Toast.LENGTH_SHORT).show();
                }

            }else{
                text_error.setVisibility(View.VISIBLE);
            }


        });
    }


    private void getCurrentLocation(){
        //Initialize task location
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                //When succes

                if(location !=null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //Initialize lat lng
                            LatLng latLng= new LatLng(location.getLatitude(),location.getLongitude());
                            //create marker options
                            MarkerOptions options= new MarkerOptions().position(latLng);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                            // Add marker on map
                            googleMap.addMarker(options);

                            placeLatitud=location.getLatitude();
                            placeLongitud=location.getLongitude();
                            String coordenadas_x=String.valueOf(location.getLatitude());
                            String coordenadas_y=String.valueOf(location.getLongitude());
                            String latlong=coordenadas_x+","+coordenadas_y;

                            String stringUrl =
                                    "https://maps.googleapis.com/maps/api/geocode/json?latlng="
                                            +latlong+"&key="+getString(string.googlemaps_autocomplete_apikey);


                            MyTask mytask=new MyTask();
                            mytask.execute(stringUrl);
                            options.title(ubitotal);
                            direccion_GOOGLE_MAPS.setText(ubitotal);



                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){

            //DESACTIVAR EL KEYBOARD
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );

            Place place = Autocomplete.getPlaceFromIntent(data);
          //  ediText.setText(place.getAddress());
            placeAddress=place.getAddress();
            placeLatitud=place.getLatLng().latitude;
            placeLongitud=place.getLatLng().longitude;
            placeName=place.getName();
            drawMarketPoint(place.getLatLng());

            convertAddress(placeAddress);

            cardView_show_gps.setVisibility(View.VISIBLE);

        }else if(resultCode== AutocompleteActivity.RESULT_ERROR){
             Status status= Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void drawMarketPoint(LatLng latLng){
        MarkerOptions markerOptions=new MarkerOptions();

        markerOptions.position(latLng);

        markerOptions.title(latLng.latitude+ " : "+latLng.longitude);

        gMap.clear();

        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));

        gMap.addMarker(markerOptions);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
       gMap=googleMap;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 44){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                //When permission grated
                // call methos
                getCurrentLocation();
            }
        }
    }





    private class MyTask extends AsyncTask<String ,Integer,String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... strings) {
            String direccion = "";
            StringBuilder builder = new StringBuilder();
            URL url = null;
            try {

                url = new URL(strings[0]);
                HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
                if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()), 8192);
                    String strLine = null;
                    while ((strLine = input.readLine()) != null) {
                        builder.append(strLine);
                        System.out.println(strLine);
                    }
                    input.close();
                }
                String jsonOutput = builder.toString();

                JSONObject jsonObject = new JSONObject(jsonOutput);
                JSONArray array=jsonObject.getJSONArray("results");
               direccion=array.getJSONObject(0).getString("formatted_address");


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return direccion;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            direccion_GOOGLE_MAPS.setText(values[0]);
            linearlayout_direccion.setBackground(getResources().getDrawable(drawable.border_editext_able));

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            placeAddress=s;
            cardView_show_gps.setVisibility(View.VISIBLE);
            convertAddress(s);
        }
    }


    public static Intent newIntentGoogleMaps(Context context,boolean showGps,boolean route){
        Intent intent= new Intent(context, GoogleMapsOficial.class);
        intent.putExtra(SHOW_GPS,showGps);
        intent.putExtra(TYPE_ROUTE,route);
        return intent;
    }


    private  void reciveData(){
        showGps=getIntent().getBooleanExtra(SHOW_GPS,false);
        route=getIntent().getBooleanExtra(TYPE_ROUTE,false);
    }

    private void  convertAddress(String address){
        //DISTRITO , CALLE , NUMERO DE LA CASA
        String[] parts = address.split(",");
        placeDetalle=parts[0];
        distrito=parts[1];
        pais=parts[2];




        //DISTRITO

        String[] direccion=parts[1].split("1|2|3|4|5|6|7|8|9|0");
        placeDistrito=direccion[0];

        direccion_GOOGLE_MAPS.setCursorVisible(true);
        direccion_GOOGLE_MAPS.setEnabled(true);
        direccion_GOOGLE_MAPS.setText(placeDetalle);

        //validarDireccion();

    }


}
