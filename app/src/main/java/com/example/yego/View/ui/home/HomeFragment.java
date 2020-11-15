package com.example.yego.View.ui.home;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.CategoriaEmpresa;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Gson.GsonCategoriaEmpresa;
import com.example.yego.Repository.Modelo.Gson.OrdenGeneralGson;
import com.example.yego.Repository.Modelo.Gson.Orden_estado_restauranteGson;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Utils.CapturedActivityPortrait;
import com.example.yego.View.HomeUI.CategoriaResultsAdapter;
import com.example.yego.View.HomeUI.FragmentCategoria;
import com.example.yego.View.InicioActivity;
import com.example.yego.View.QrCodeUI.QrCodeActivity;
import com.example.yego.View.SearchUI.SearchActivity;
import com.example.yego.View.SubCategoriaEmpresaActivity;
import com.example.yego.View.SubCategoriaEmpresaUI.FragmentEmpresaSort;
import com.example.yego.View.SubCategoriaEmpresaUI.FragmentUbicacionCerca;
import com.example.yego.ViewModel.CategoriaEmpresaViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.yego.Login.MyApp.CHANNEL_1_ID;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.example.yego.Login.MyApp.CHANNEL_1_ID;


public class HomeFragment extends Fragment implements   CategoriaResultsAdapter.OnNoteListener{

    private LinearLayout home_LOCATION,home_SEARCH,linearLayout_orden_enable;

    private TextView activity_home_NOMBRE_UBICACION,cantidad_total_producto_carrito;

    private NavController mNavController;

    private ImageView scanner;

    private GsonCategoriaEmpresa mGsonCategoriaEmpresa;

    private CategoriaEmpresaViewModel viewModel;
    private CategoriaResultsAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CategoriaResultsAdapter();

        viewModel = ViewModelProviders.of(this).get(CategoriaEmpresaViewModel.class);
        viewModel.init();
        viewModel.getCategoiraEmpresaLiveData().observe(this, new Observer<GsonCategoriaEmpresa>() {
            @Override
            public void onChanged(GsonCategoriaEmpresa gsonCategoriaEmpresa) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                if(gsonCategoriaEmpresa !=null){

                    adapter.setResults(gsonCategoriaEmpresa.getListaCategoriaEmpresa(),HomeFragment.this::onNoteClick);
                    mGsonCategoriaEmpresa=gsonCategoriaEmpresa;


                }
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        declararWidgets(view);

        mNavController= Navigation.findNavController(view);

        buttonLocation();

        buttonSearch();

        setDataWidget();

        funPusherUpdateStatePedido();

        buttonScanner();

        categoria(view);

        showOrden();

        clickOrdenPendiente();


        if(getArguments()!=null){

            clickOrdenPendiente();

        }




    }

    private void categoria(View view){
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);


        viewModel.searchCategoriaEmpresa();

        RecyclerView recyclerView=view.findViewById(R.id.recycler_view_categoria);
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setAdapter(adapter);

    }

    private void declararWidgets(View view){

        home_LOCATION=view.findViewById(R.id.home_LOCATION);
        home_SEARCH=view.findViewById(R.id.home_SEARCH);
        activity_home_NOMBRE_UBICACION=view.findViewById(R.id.activity_home_NOMBRE_UBICACION);
        linearLayout_orden_enable=view.findViewById(R.id.linearLayout_orden_enable);
        cantidad_total_producto_carrito=view.findViewById(R.id.cantidad_total_producto_carrito);

        //orden_pendiente=view.findViewById(R.id.orden_pendiente);
        scanner=view.findViewById(R.id.scanner);

        TextView nombre_usuario = view.findViewById(R.id.nombre_usuario);
        String[] args=Cliente_Bi.getCliente().getNombre().split(" ");
        String nombre =args[0].substring(0, 1).toUpperCase() + args[0].substring(1);


        nombre_usuario.setText(nombre);
    }

    private void buttonLocation(){

        home_LOCATION.setOnClickListener( v->{
                    mNavController.navigate(R.id.nav_ubicaciones);
        }
        );
    }

    private void buttonSearch(){

        home_SEARCH.setOnClickListener( v->{
                   // mNavController.navigate(R.id.searchFragment);
            Intent intent=SearchActivity.newIntent(getContext(),mGsonCategoriaEmpresa);
            startActivity(intent);
                }
        );
    }

    private void setDataWidget(){
        activity_home_NOMBRE_UBICACION.setText(Ubicacion.ubicacionEnable.getUbicacion_nombre());
    }


    private void buttonScanner(){

        scanner.setOnClickListener( v->{

            Intent intent=new Intent(getContext(), QrCodeActivity.class);
            startActivity(intent);
           /* IntentIntegrator intent = new IntentIntegrator(getActivity());
            intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            intent.setPrompt("ESCANEAR CODIGO QR");
            intent.setCameraId(0);
            intent.setCaptureActivity(CapturedActivityPortrait.class);
            intent.setOrientationLocked(false);
            intent.setBeepEnabled(false);
            intent.setBarcodeImageEnabled(false);
            intent.initiateScan();*/
        });


    }

    private void clickOrdenPendiente(){
        linearLayout_orden_enable.setOnClickListener(v->{
            mNavController.navigate(R.id.fragmentListOrden);
        });
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

                            //orden_pendiente.setVisibility(View.VISIBLE);
                            Orden_estado_restauranteGson object = gson.fromJson(mJson, Orden_estado_restauranteGson.class);
                            String mensaje="Nuevo estado"+object.getListaOrden_estado_general().get(0).getIdestadogeneral()+
                                    " - "+object.getListaOrden_estado_general().get(object.getListaOrden_estado_general().size()-1).getFecha();
                            //orden_pendiente.setText(mensaje);
                            //Toast.makeText(getContext(), "Nueva Actualizacion "+object.getListaOrden_estado_restaurante().get(0).getId().getIdestado_venta(), Toast.LENGTH_SHORT).show();
                            publishNotification(mensaje);

                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }

        });
        InicioActivity.pusher.connect();
    }


    private void publishNotification(String mensaje){
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        int icono = R.mipmap.ic_launcher;
        Intent i=InicioActivity.newIntentInicioActivity(getContext(),true);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);

        mBuilder =new NotificationCompat.Builder(getApplicationContext())
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle("Yegoo")
                .setContentText(mensaje)
                .setVibrate(new long[] {100, 250, 100, 500})
                .setAutoCancel(true);



        mNotifyMgr.notify(1, mBuilder.build());

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("datos");

        IntentResult results=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        System.out.println("datos"+results.getContents());

        if(results!=null){
            if(results.getContents()==null){
                Toast.makeText(getContext(),"CANCELASTE EL ESCANEO",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(),"VAOR DEL QR"+results.getContents(),Toast.LENGTH_SHORT).show();
            }

        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onNoteClick(CategoriaEmpresa categoriaEmpresa) {

        Intent intent= SubCategoriaEmpresaActivity.newIntentSubCategoriaActivity(getContext(),categoriaEmpresa,mGsonCategoriaEmpresa);
        startActivity(intent);

    }

    private void showOrden(){

        if(OrdenGeneralGson.cantidadOrden>0){

            linearLayout_orden_enable.setVisibility(View.VISIBLE);

            cantidad_total_producto_carrito.setText(String.valueOf(OrdenGeneralGson.cantidadOrden));

        }else{

            linearLayout_orden_enable.setVisibility(View.GONE);

        }

    }



}