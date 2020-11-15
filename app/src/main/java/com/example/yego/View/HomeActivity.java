package com.example.yego.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Gson.GsonProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.Utils.ActionBottomDialogFragment;
import com.example.yego.ViewModel.ProductoJOINregistroPedidoJOINpedidoViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

public class HomeActivity extends AppCompatActivity {
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayout home_LOCATION;
    private ImageView home_PERFIL,home_MENU,home_ICON_HOME,home_ICON_BAG,home_ICON_FAVORITE,home_ICON_ORDEN;
    private SearchView home_SEARCH;
    private TextView activity_home_NOMBRE_UBICACION;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
/*
        //LOAD DATA CARRITO
        loadDataCarrito();

        //DECLARAR WIDGETS
        declararWidgets();

        //SET DATA
        setDataWidget();

        //TOUCH HOME_LOCATION
        touch_homeLOCATION();

        //TOUCH CARRITO
        touch_homeICONBAG();*/




    }
/*
    private void setDataWidget(){
        String ubicacion="";
        if(Ubicacion.ubicacionEnable==null){
            ubicacion="load";
        }else{
            ubicacion=Ubicacion.ubicacionEnable.getMaps_detalle();
        }
        activity_home_NOMBRE_UBICACION.setText(ubicacion);
    }

    private void touch_homeICONBAG() {

        home_ICON_BAG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=CarritoGeneralActivty.newIntentCarritoActivity2(getApplicationContext());
                startActivity(intent);

            }
        });
    }

    private void touch_homeLOCATION(){

        home_LOCATION.setOnClickListener(view -> {

            ActionBottomDialogFragment addPhotoBottomDialogFragment =
                    ActionBottomDialogFragment.newInstance();
            addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                    ActionBottomDialogFragment.TAG);
        });

        home_ICON_ORDEN.setOnClickListener(view->{


            Intent intent= new Intent(view.getContext(), OrdenActivity.class);
            startActivity(intent);
        });
    }*/

 /*   private void declararWidgets(){
        home_LOCATION=findViewById(R.id.home_LOCATION);
        home_ICON_HOME=findViewById(R.id.home_ICON_HOME);
        home_ICON_BAG=findViewById(R.id.home_ICON_BAG);
        home_ICON_FAVORITE=findViewById(R.id.home_ICON_FAVORITE);
        home_ICON_ORDEN=findViewById(R.id.home_ICON_ORDEN);
        home_PERFIL=findViewById(R.id.home_PERFIL);
        home_MENU=findViewById(R.id.home_MENU);
        home_SEARCH=findViewById(R.id.home_SEARCH);
        activity_home_NOMBRE_UBICACION=findViewById(R.id.activity_home_NOMBRE_UBICACION);
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
                }
            }
        });
        viewModel.listaCarritoByUsuario(Cliente_Bi.getCliente().getIdusuario());

    }
*/

}
