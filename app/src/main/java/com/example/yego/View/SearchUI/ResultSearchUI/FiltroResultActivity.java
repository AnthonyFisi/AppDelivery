package com.example.yego.View.SearchUI.ResultSearchUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.CategoriaEmpresa;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonCategoriaEmpresa;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.View.EmpresaDetailActivity;
import com.example.yego.View.EmpresaDetailUI.BuscadorUI.BuscadorActivity;
import com.example.yego.View.SearchUI.SearchActivity;
import com.example.yego.View.SubCategoriaEmpresaUI.EmpresaSortResultsAdapter;
import com.example.yego.ViewModel.EmpresaViewModel;

public class FiltroResultActivity extends AppCompatActivity implements EmpresaSortResultsAdapter.ImageListener {

    private static final String GSONCATEGORIA_EMPRESA = "gsoncategoria_empresa";
    private static final String IDCATEGORIAEMPRESA = "idcategoriaempresa";
    private static final String DISTANCIA ="distancia" ;
    private static final String PRECIODELIVERY = "preciodelivery";

    private GsonCategoriaEmpresa gsonCategoriaEmpresa;
    private int idcategoriaempresa,distancia;
    private Float preciodelivery;

    private EmpresaViewModel mEmpresaViewModel;

    private TextView precio_delivery,distancia_delivery,categoria;

    private LinearLayout id_result_succesful,id_result_empty;

    private ProgressBar load_data;

    private Toolbar mToolbar;

    private RecyclerView recyclerView_resultado;

    private EmpresaSortResultsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_result);
        reciveData();
        initDataLoad();
        declararWidget();
        setDataWidget();
        mToolbar.setNavigationOnClickListener(v->{
            onBackPressed();
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater= getMenuInflater();

        menuInflater.inflate(R.menu.search,menu);

        MenuItem menuItem=menu.findItem(R.id.action_search);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent intent=SearchActivity.newIntent(FiltroResultActivity.this,gsonCategoriaEmpresa);
                startActivity(intent);
                finish();

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }

    private void initDataLoad(){

        adapter=new EmpresaSortResultsAdapter();

        mEmpresaViewModel=new ViewModelProvider(this).get(EmpresaViewModel.class);
        mEmpresaViewModel.init();
        mEmpresaViewModel.getListaEmpresanLiveData().observe(this, new Observer<GsonEmpresa>() {
            @Override
            public void onChanged(GsonEmpresa gsonEmpresa) {
                load_data.setVisibility(View.GONE);
                if(gsonEmpresa!=null){
                    id_result_succesful.setVisibility(View.VISIBLE);
                    id_result_empty.setVisibility(View.GONE);

                    adapter.setEmpresaSortAdpater(gsonEmpresa.getListaEmpresa(),FiltroResultActivity.this);
                    recyclerView_resultado.setAdapter(adapter);
                }else {

                    id_result_empty.setVisibility(View.VISIBLE);
                    id_result_succesful.setVisibility(View.GONE);

                }
            }
        });

        String ubicacion= Cliente_Bi.getCliente().getMaps_coordenada_x()+","+Cliente_Bi.getCliente().getMaps_coordenada_y();


        mEmpresaViewModel.filtroEmpresa(idcategoriaempresa,distancia,preciodelivery,ubicacion);

    }

    private void declararWidget(){
        id_result_empty=findViewById(R.id.id_result_empty);
        id_result_succesful=findViewById(R.id.id_result_succesful);
        load_data=findViewById(R.id.load_data);
        mToolbar=findViewById(R.id.toolbar8);
        precio_delivery=findViewById(R.id.precio_delivery);
        categoria=findViewById(R.id.categoria);
        distancia_delivery=findViewById(R.id.distancia_delivery);
        recyclerView_resultado=findViewById(R.id.recyclerView_resultado);
    }

    private void setDataWidget(){
        id_result_succesful.setVisibility(View.GONE);
        id_result_empty.setVisibility(View.GONE);

        setSupportActionBar(mToolbar);

        String price=" S/ "+preciodelivery;
        precio_delivery.setText(price);

        String distance=distancia+" m ";
        distancia_delivery.setText(distance);

        categoria.setText(findCategoryName());


        recyclerView_resultado.setLayoutManager(new LinearLayoutManager(FiltroResultActivity.this, LinearLayoutManager.VERTICAL, false));


    }

    public static Intent newIntent(Context context, GsonCategoriaEmpresa gsonCategoriaEmpresa,int idcategoriaempresa,int distancia,float preciodelivery){
        Intent intent= new Intent(context, FiltroResultActivity.class);
        intent.putExtra(GSONCATEGORIA_EMPRESA,gsonCategoriaEmpresa);
        intent.putExtra(IDCATEGORIAEMPRESA,idcategoriaempresa);
        intent.putExtra(DISTANCIA,distancia);
        intent.putExtra(PRECIODELIVERY,preciodelivery);
        return intent;
    }


    private  void reciveData(){
        if(getIntent().getSerializableExtra(GSONCATEGORIA_EMPRESA)!=null){
            gsonCategoriaEmpresa=(GsonCategoriaEmpresa) getIntent().getSerializableExtra(GSONCATEGORIA_EMPRESA);
        }

        idcategoriaempresa=getIntent().getIntExtra(IDCATEGORIAEMPRESA,0);
        distancia=getIntent().getIntExtra(DISTANCIA,0);
        preciodelivery=getIntent().getFloatExtra(PRECIODELIVERY,0);

    }

    private String findCategoryName(){
        String nombreCategoria="";
        for(CategoriaEmpresa categoria:gsonCategoriaEmpresa.getListaCategoriaEmpresa()){
            if(categoria.getIdCategoriaEmpresa()==idcategoriaempresa){
                nombreCategoria=categoria.getNombre_categoria();
            }
        }
        return nombreCategoria;
    }

    @Override
    public void imageClick(Empresa empresa) {
        Intent intent= EmpresaDetailActivity.newIntentEmpresa(FiltroResultActivity.this,empresa);
        startActivity(intent);
    }
}
