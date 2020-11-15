package com.example.yego.View;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yego.Repository.Modelo.Gson.GsonOrden;
import com.example.yego.Repository.Modelo.Gson.GsonProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.Gson.OrdenGeneralGson;
import com.example.yego.Repository.Modelo.Gson.Orden_estado_restauranteGson;
import com.example.yego.Repository.Modelo.Gson.RepartidorInformationGson;
import com.example.yego.Repository.Modelo.Orden_estado_general;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.Utils.ActionBottomDialogFragment;
import com.example.yego.View.OrdenUI.FragmentListOrden;
import com.example.yego.View.ui.Perfil.FragmentPhoneDialog;
import com.example.yego.View.ui.Perfil.PerfilFragment;
import com.example.yego.View.ui.share.ShareFragment;
import com.example.yego.ViewModel.ProductoJOINregistroPedidoJOINpedidoViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Slide;

import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.yego.Login.MyApp.CHANNEL_1_ID;
import static com.example.yego.R.*;
import static com.example.yego.R.string.*;


public class InicioActivity extends AppCompatActivity implements ActionBottomDialogFragment.BackToInicio ,
        PerfilFragment.BackToInicio ,
        FragmentPhoneDialog.FormDialogListener1,
        FragmentListOrden.BackToInicio,
        ShareFragment.BackToInicio
{

    private static final String CLIENTE_BI = "cliente_bi";
    private static final String VENTA_REALIZADA = "venta_realizada" ;
    private AppBarConfiguration mAppBarConfiguration;
    private BottomNavigationView bottomNavView;
    private boolean venta_realizada;
    private NavigationView navigationView;

    private static final String CANTIDAD = "cantidad";

    private NavController nav;

    private NavGraph graph;

    private NavController navController;

    //INICIALIZAR EL PUSHER PARA RECIBIR NOTIFICACIONES
    public static Pusher pusher;
    public static Channel channel ;
    public static Channel channel_repartidor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_inicio);

        reciveData();

        loadDataCarrito();

        initPusher();

        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(id.nav_host_fragment);
        nav= navHost.getNavController();

        NavInflater navInflater = nav.getNavInflater();
        graph = navInflater.inflate(navigation.mobile_navigation);

        if (venta_realizada) {

            graph.setStartDestination(id.fragmentListOrden);

        } else {
            graph.setStartDestination(id.nav_inicio);
        }



        nav.setGraph(graph);

        DrawerLayout drawer = findViewById(id.drawer_layout);
        navigationView = findViewById(id.nav_view);

        bottomNavView=findViewById(id.bottom_nav_view);



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                id.nav_inicio, id.nav_perfil, id.nav_ganar_dinero,
                id.nav_socio, id.nav_soporte, id.nav_cerrar_sesion, id.fragmentListOrden, id.detalleOrdenFragment,
                id.nav_ubicaciones, id.empresaFavoritoFragment)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, id.nav_host_fragment);

        NavigationUI.setupWithNavController(bottomNavView, navController);

        // NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            bottomNavView.setVisibility(View.GONE);



/*
            if(R.id.nav_inicio == destination.getId() ||
                    R.id.fragmentListOrden == destination.getId() ||
                    R.id.empresaFavoritoFragment == destination.getId()
            ){
                bottomNavView.setVisibility(View.VISIBLE);
            }else{

                bottomNavView.setVisibility(View.GONE);
            }*/


        });

        initDataDrawer();

        funPusher();

        funPusherRepartidor();

    }

    // INICIALIAMOS LA SUSCIPTCION AL PUSHER
    private void initPusher() {
        PusherOptions options = new PusherOptions();
        options.setCluster(getString(pusher_cluster));

        pusher = new Pusher( getString(pusher_apikey), options);
        //ESTE ES EL CANAL DE COMUNICACAION DE LOS ESTADOS DEL PEDIDO (aceptado->en preparacion -> listo-> en camino)
        channel= InicioActivity.pusher.subscribe(getString(pusher_canal_pedido)+Cliente_Bi.getCliente().getIdusuario());

        //ESTE ES EL CANAL DE COMUNICACION DEL REPARTIDOR
        channel_repartidor=InicioActivity.pusher.subscribe(getString(pusher_canal_delivery)+Cliente_Bi.getCliente().getIdusuario());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    public static Intent newIntentInicioActivity(Context context,boolean venta){
        Intent intent= new Intent(context,InicioActivity.class);
        intent.putExtra(VENTA_REALIZADA,venta);
        return intent;
    }

    public void initDataDrawer(){

        View hView =  navigationView.getHeaderView(0);
        TextView nombre_usuario=hView.findViewById(id.nombre_usuario);
        TextView correo_usuario=hView.findViewById(id.correo_usuario);

        Cliente_Bi cliente_bi=Cliente_Bi.getCliente();
        correo_usuario.setText(cliente_bi.getCorreo());
        nombre_usuario.setText(cliente_bi.getNombre());

        ImageView imageView_USUARIO = hView.findViewById(id.imageView_USUARIO);

        if (cliente_bi.getFoto()!= null) {
            String imageUrl = cliente_bi.getFoto()
                    .replace("http://", "https://");

            Glide.with(this)
                    .load(imageUrl)
                    .into(imageView_USUARIO);
        }
    }

    private  void reciveData(){


        venta_realizada=getIntent().getBooleanExtra(VENTA_REALIZADA,false);


    }


    private void loadDataCarrito(){
        ProductoJOINregistroPedidoJOINpedidoViewModel viewModel;
        viewModel = ViewModelProviders.of(this).get(ProductoJOINregistroPedidoJOINpedidoViewModel.class);
        viewModel.init();
        viewModel.getProductoJOINregistroPedidoJOINpedidoLiveData().observe(this, new Observer<GsonProductoJOINregistroPedidoJOINpedido>() {

            @Override
            public void onChanged(GsonProductoJOINregistroPedidoJOINpedido gsonProductoJOINregistroPedidoJOINpedido) {
                if(gsonProductoJOINregistroPedidoJOINpedido !=null){

                    if(ProductoJOINregistroPedidoJOINpedido.carrito.isEmpty()){

                        ProductoJOINregistroPedidoJOINpedido.carrito=gsonProductoJOINregistroPedidoJOINpedido.getListaProductoJOINregistroPedidoJOINpedido();


                    }

                    if(gsonProductoJOINregistroPedidoJOINpedido.getCantidadOrden()>0){

                        OrdenGeneralGson.cantidadOrden=gsonProductoJOINregistroPedidoJOINpedido.getCantidadOrden();
                        Bundle bundle =new Bundle();
                        bundle.putString(CANTIDAD,String.valueOf(gsonProductoJOINregistroPedidoJOINpedido.getCantidadOrden()));
                        navController.setGraph(navController.getGraph(),bundle);
                    }




                }
            }
        });
        viewModel.listaCarritoByUsuario(Cliente_Bi.getCliente().getIdusuario());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult results= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(results!=null){
            if(results.getContents()==null){
                Toast.makeText(this,"CANCELASTE EL ESCANEO",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"VAOR DEL QR"+results.getContents(),Toast.LENGTH_SHORT).show();
            }

        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void back() {
        graph.setStartDestination(id.nav_inicio);
        nav.setGraph(graph);
    }

    @Override
    public void update() {
        Bundle bundle=new Bundle();
        bundle.putString("codigo","hola");
        navController.setGraph(navController.getGraph(),bundle);
    }


        private void funPusher(){
            InicioActivity.channel.bind("my-event", (channelName, eventName, data) -> {
                System.out.println(data+"ACTIVITY INICIO");

                try{

                    JsonParser parser = new JsonParser();
                    JsonElement mJson =  parser.parse(data);
                    Gson gson = new Gson();

                    Orden_estado_restauranteGson object = gson.fromJson(mJson, Orden_estado_restauranteGson.class);



                    String numeroPedido="Pedido "+object.getListaOrden_estado_general().get(0).getIdventa();

                    String message=menssageState(object.getListaOrden_estado_general().get(object.getListaOrden_estado_general().size()-1));

                    createNotificationChannel();

                    publishNotification(numeroPedido,message);

                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

            });
            InicioActivity.pusher.connect();

            //InicioActivity.pusher.
        }




        private void funPusherRepartidor(){
            InicioActivity.channel_repartidor.bind("my-event", (channelName, eventName, data) -> {
                System.out.println(data+"ACTIVITY INICIO REPARTIDOR");

                try{

                    JsonParser parser = new JsonParser();
                    JsonElement mJson =  parser.parse(data);
                    Gson gson = new Gson();
                    RepartidorInformationGson object = gson.fromJson(mJson, RepartidorInformationGson.class);

                    String title="Repartidor asignado";

                    String message=object.getUsuario_general().getNombre()+" "+object.getUsuario_general().getApellido();


                    createNotificationChannel();

                    publishNotification(title,message);

                }catch (Exception e){

                    System.out.println(e.getMessage());
                }

            });
            InicioActivity.pusher.connect();
        }









    private String menssageState(Orden_estado_general orden_estado_general){

        String message = "";

        if(orden_estado_general.getIdestadogeneral()==1) {
            message = getResources().getString(estado_orden_1);
        }
        if(orden_estado_general.getIdestadogeneral()==2){
            message=getResources().getString(estado_orden_2);
        }
        if(orden_estado_general.getIdestadogeneral()==3){
            message= getResources().getString(estado_orden_3);
        }
        if(orden_estado_general.getIdestadogeneral()==4){
            message= getResources().getString(estado_orden_4);
        }
        if(orden_estado_general.getIdestadogeneral()==5){
            message= getResources().getString(estado_orden_5);
        }
        if(orden_estado_general.getIdestadogeneral()==6){
           message=getResources().getString(estado_orden_6);
        }

        return message;
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name="Notification";
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_1_ID,
                    name,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }

    }

    private void publishNotification(String titulo,String mensaje){

        Intent i=InicioActivity.newIntentInicioActivity(this,true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(mipmap.ic_launcher_yego)

                .setContentIntent(pendingIntent)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        notificationManager.notify(1, notification);
    }


}

