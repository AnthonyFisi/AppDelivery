package com.example.yego.View.OrdenUI.SupervisarOrden;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Gson.GsonOrden;
import com.example.yego.Repository.Modelo.Gson.Orden_estado_restauranteGson;
import com.example.yego.Repository.Modelo.Gson.RepartidorInformationGson;
import com.example.yego.Repository.Modelo.Orden;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.View.InicioActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentSupervisarOrden  extends Fragment implements OnMapReadyCallback {

    private static final String  PASS_DATA_SUPERVISAR="com.example.yego.View.OrdenUI.SupervisarOrden.FragmentSupervisarOrden";


    private GoogleMap mMap;
    private ArrayList markerPoints= new ArrayList();
    private GsonOrden orden;
    private TextView fragment_supervisar_orden_NOMBRE_UBICACION,fragment_supervisar_orden_HORARIO,fragment_supervisar_orden_NOMBRE_EMPRESA;
    private ImageView fragment_supervisar_orden_IMAGEN_EMPRESA,fragment_supervisar_orden_NUMERO_CELULAR,fragment_supervisar_orden_WHATSAPP;
    private Button fragment_supervisar_orden_DETALLE_PEDIDO;

    private CardView back_inicio,notificacion_repartidor;

    private LinearLayout fragment_supervisar_orden_BUSCANDO_REPARTIDOR,fragment_supervisar_orden_REPARTIDOR;

    public FragmentSupervisarOrden() {
    }


    public static FragmentSupervisarOrden newInstance(Orden orden) {
        FragmentSupervisarOrden fragment = new FragmentSupervisarOrden();
        Bundle args = new Bundle();
        args.putSerializable(PASS_DATA_SUPERVISAR, orden);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
           orden=(Orden) getArguments().getSerializable(PASS_DATA_SUPERVISAR);
            System.out.println("LLEGA LOS DATOS");
        }else{
            Toast.makeText(getContext(),"NO HAY DATOS",Toast.LENGTH_SHORT).show();
            System.out.println("NOOOOOOOOOOOOOOOOOOOOOO  LLEGA LOS DATOS");

        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_supervisar_orden, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null){

            FragmentSupervisarOrdenArgs args=FragmentSupervisarOrdenArgs.fromBundle(getArguments());
            orden=args.getOrden();
        }


        MapView mapView = (MapView) view.findViewById(R.id.google_map_supervisar_orden);

        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        mapView.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment

        declararWidgets(view);
        // setDataWidget();
        setDataWidget();

        clickButton();

        funPusherRepartidor();

        funPusherUpdateStatePedido();



    }

    private void declararWidgets(View view){
        fragment_supervisar_orden_NOMBRE_UBICACION=view.findViewById(R.id.fragment_supervisar_orden_NOMBRE_UBICACION);
        fragment_supervisar_orden_HORARIO=view.findViewById(R.id.fragment_supervisar_orden_HORARIO);
        fragment_supervisar_orden_IMAGEN_EMPRESA=view.findViewById(R.id.fragment_supervisar_orden_IMAGEN_EMPRESA);
        fragment_supervisar_orden_NOMBRE_EMPRESA=view.findViewById(R.id.fragment_supervisar_orden_NOMBRE_REPARTIDOR);
        fragment_supervisar_orden_NUMERO_CELULAR=view.findViewById(R.id.fragment_supervisar_orden_NUMERO_CELULAR);
        fragment_supervisar_orden_WHATSAPP=view.findViewById(R.id.fragment_supervisar_orden_WHATSAPP);
        fragment_supervisar_orden_DETALLE_PEDIDO=view.findViewById(R.id.fragment_supervisar_orden_DETALLE_PEDIDO);

        fragment_supervisar_orden_REPARTIDOR=view.findViewById(R.id.fragment_supervisar_orden_REPARTIDOR);
        fragment_supervisar_orden_BUSCANDO_REPARTIDOR=view.findViewById(R.id.fragment_supervisar_orden_BUSCANDO_REPARTIDOR);
        //estado_pedido_supervisar=view.findViewById(R.id.estado_pedido_supervisar);

        back_inicio=view.findViewById(R.id.back_inicio);
        notificacion_repartidor=view.findViewById(R.id.notificacion_repartidor);


    }

    private void setDataWidget(){

        fragment_supervisar_orden_NOMBRE_UBICACION.setText(orden.getDetalle_orden().getUbicacion_nombre());

        fragment_supervisar_orden_HORARIO.setText(orden.getDetalle_orden().getHorario_nombre());

        fragment_supervisar_orden_REPARTIDOR.setVisibility(View.GONE);
        fragment_supervisar_orden_BUSCANDO_REPARTIDOR.setVisibility(View.GONE);

        if(orden.getUsuario()!=null){

            setDataRepartidor();

        }else{
            fragment_supervisar_orden_BUSCANDO_REPARTIDOR.setVisibility(View.VISIBLE);
        }

        back_inicio.setOnClickListener(v->{
            getActivity().onBackPressed();
        });

        fragment_supervisar_orden_NUMERO_CELULAR.setOnClickListener(v->{
            Intent intent= new Intent((Intent.ACTION_CALL));
            intent.setData(Uri.parse("tel"+orden.getUsuario().getCelular().trim()));
            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE )!= PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(),"Habilitar el permiso",Toast.LENGTH_SHORT).show();
                requestPermission();
            }else{
                startActivity(intent);
            }
        });

    }


    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(), new String []{Manifest.permission.CALL_PHONE},1);
    }

    private void setDataRepartidor(){
        fragment_supervisar_orden_REPARTIDOR.setVisibility(View.VISIBLE);
        fragment_supervisar_orden_BUSCANDO_REPARTIDOR.setVisibility(View.GONE);
        if (orden.getUsuario().getFoto()!= null) {
            String imageUrl = orden.getDetalle_orden().getUrlfoto_empresa()
                    .replace("http://", "https://");

            Glide.with(this)
                    .load(imageUrl)
                    .into(fragment_supervisar_orden_IMAGEN_EMPRESA);
        }

        String nombre_apellido=orden.getUsuario().getNombre()+orden.getUsuario().getApellido();
        fragment_supervisar_orden_NOMBRE_EMPRESA.setText(nombre_apellido);
    }


    private void clickButton(){

        fragment_supervisar_orden_NUMERO_CELULAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fragment_supervisar_orden_WHATSAPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fragment_supervisar_orden_DETALLE_PEDIDO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                NavController nav=Navigation.findNavController(view);

               // FragmentSupervisarOrdenDirections.ActionFragmentSupervisarOrdenToDetalleOrdenFragment action=FragmentSupervisarOrdenDirections.actionFragmentSupervisarOrdenToDetalleOrdenFragment(orden);
               // nav.navigate(action);

               // Navigation.findNavController().navigate(R.id.detalleOrdenFragment);
                //DetalleOrdenFragment fragment=DetalleOrdenFragment.newInstance(orden);
                //fragment.show(getActivity().getSupportFragmentManager(),DetalleOrdenFragment.TAG);

            }
        });
    }


    public void setPassOrden(GsonOrden orden){
       // this.orden=orden;
        //setDataWidget();
    }

    private void drawRoute(){


       double latOri= Double.valueOf(orden.getDetalle_orden().getEmp_maps_coordenada_x().trim());
        double logOri=Double.valueOf(orden.getDetalle_orden().getEmp_maps_coordenada_Y().trim());


        LatLng locationOrigen = new LatLng(latOri,logOri );


        markerPoints.add(locationOrigen);

        // Creating MarkerOptions
        MarkerOptions options2 = new MarkerOptions();

        // Setting the position of the marker
        options2.position(locationOrigen);
        options2.icon(BitmapDescriptorFactory.fromResource(R.drawable.store1));

        String nombre_estado=orden.getDetalle_orden().getNombre_empresa()+" "+estado_pedido(orden);

        options2.title(nombre_estado);
        mMap.addMarker(options2).showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationOrigen, 16));

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        drawRoute();
        listenChangeLocation();
    }


    @SuppressLint("StaticFieldLeak")
    private class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(10);

                lineOptions.color(Color.rgb(5,175,242));
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";
        //API KEY
        String API_KEY="&key=AIzaSyAovb3NQYJdlU_a8SwdrWIe2cj-e2NWOmM";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters+API_KEY;

        /*String m=
                "https://maps.googleapis.com/maps/api/directions/json?origin=-12.1689212,-76.9275876&detination=-12.1690929,-76.9277597&key=AIzaSyAovb3NQYJdlU_a8SwdrWIe2cj-e2NWOmM"
*/
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    private void funPusherRepartidor(){
        InicioActivity.channel_repartidor.bind("my-event", (channelName, eventName, data) -> {
            System.out.println(data+"los datossssssssssssssssss");

            if(getActivity() !=null){

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            JsonParser parser = new JsonParser();
                            JsonElement mJson =  parser.parse(data);
                            Gson gson = new Gson();


                            RepartidorInformationGson object = gson.fromJson(mJson, RepartidorInformationGson.class);

                            orden.setUsuario(object.getUsuario_general());

                            setDataRepartidor();

                            listenChangeLocation();


                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }

        });
        InicioActivity.pusher.connect();
    }


    private void listenChangeLocation(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if(orden.getUsuario()!=null){
            DocumentReference docRef = db.collection("UsuariosDelivery").document(String.valueOf(orden.getUsuario().getIdusuariogeneral().intValue()));

            docRef.addSnapshotListener((documentSnapshot, e) -> {

                if (e != null) {
                    System.err.println("Listen failed: " + e);
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {

                    System.out.println("Current data: " + documentSnapshot.getData());

                    drawDeliveryPosition(documentSnapshot);

                } else {
                    System.out.print("Current data: null");
                }

            });
        }



    }


    private void drawDeliveryPosition(DocumentSnapshot data){

        HashMap<String, Object> value = (   HashMap<String, Object>) data.getData();

        mMap.clear();


        String locationString=value.get("location").toString();

        String[] coordenada=locationString.split(",");

        double lat = Double.parseDouble(coordenada[0]);

        double lng = Double.parseDouble(coordenada[1]);

        LatLng location = new LatLng(lat, lng);


        MarkerOptions markerOptions=new MarkerOptions();

        markerOptions.position(location);

       // markerOptions.title(location.latitude+ " : "+location.longitude);

        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.motorcycle1));

        //mMap.clear();

        if(orden.getLista_orden_general().get(orden.getLista_orden_general().size()-1).getIdestadogeneral()==3
                || orden.getLista_orden_general().get(orden.getLista_orden_general().size()-1).getIdestadogeneral()==4
        ){

            markerOptions.title(orden.getDetalle_orden().getNombre_empresa());
        }


        mMap.addMarker(markerOptions).showInfoWindow();



        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15));


        //mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300));

        double lat2 = Double.parseDouble(orden.getDetalle_orden().getMaps_coordenada_x());

        double lng2 = Double.parseDouble(orden.getDetalle_orden().getEmp_maps_coordenada_Y());

        LatLng location2 = new LatLng(lat2, lng2);


        MarkerOptions markerOptions2=new MarkerOptions();

        markerOptions2.position(location2);

        if(orden.getLista_orden_general().get(orden.getLista_orden_general().size()-1).getIdestadogeneral()==1
         || orden.getLista_orden_general().get(orden.getLista_orden_general().size()-1).getIdestadogeneral()==2
                || orden.getLista_orden_general().get(orden.getLista_orden_general().size()-1).getIdestadogeneral()==6
        ){

            markerOptions2.title(orden.getDetalle_orden().getNombre_empresa());

        }


        markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_tienda));



        mMap.addMarker(markerOptions2).showInfoWindow();



        double lat3 = Double.parseDouble(Cliente_Bi.getCliente().getMaps_coordenada_x());

        double lng3 = Double.parseDouble(Cliente_Bi.getCliente().getMaps_coordenada_y());

        LatLng location3 = new LatLng(lat3, lng3);


        MarkerOptions markerOptions3=new MarkerOptions();

        markerOptions3.position(location3);


        markerOptions3.icon(BitmapDescriptorFactory.fromResource(R.drawable.houseicon));



        mMap.addMarker(markerOptions3);


    }


    private void funPusherUpdateStatePedido(){
        InicioActivity.channel.bind("my-event", (channelName, eventName, data) -> {
            System.out.println(data+"los datossssssssssssssssss");

            if(getActivity() !=null){

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            JsonParser parser = new JsonParser();
                            JsonElement mJson =  parser.parse(data);
                            Gson gson = new Gson();


                            Orden_estado_restauranteGson object = gson.fromJson(mJson, Orden_estado_restauranteGson.class);

                            orden.setLista_orden_general(object.getListaOrden_estado_general());

                          //  setDataRepartidor();

                            String mensaje_estado="Nuevo estado"
                                    +object.getListaOrden_estado_general().get(object.getListaOrden_estado_general().size()-1).getIdestadogeneral()
                                    +" - "+
                                    object.getListaOrden_estado_general().get((object.getListaOrden_estado_general().size()-1)).getFecha();

                            //estado_pedido_supervisar.setText(mensaje_estado);


                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }

        });
        InicioActivity.pusher.connect();
    }

    //orden.getLista_orden_estado().get(orden.getLista_orden_estado().size()-1)

    private String estado_pedido(GsonOrden orden){

        String estado="";

        if(orden.getDetalle_orden().getIdestado_general()==2 ){
            //EL PEDIDO YA FUE CONFIRMADO Y ESTA SIENDO PREPARADO
            estado="Orden preparandose";

        }


        if(orden.getDetalle_orden().getIdestado_general()==3 ){
            //EL PEDIDO YA FUE CONFIRMADO Y ESTA SIENDO PREPARADO
            estado="Orden lista";
        }

        return estado;
    }




}
