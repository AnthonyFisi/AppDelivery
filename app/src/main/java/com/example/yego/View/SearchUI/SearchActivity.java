package com.example.yego.View.SearchUI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.CategoriaEmpresa;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonCategoriaEmpresa;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.View.SearchUI.ResultSearchUI.FiltroResultActivity;
import com.example.yego.View.SearchUI.ResultSearchUI.ResultSearchActivity;
import com.example.yego.ViewModel.EmpresaViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity implements ItemSearchResultsAdapter.Filtro{

    private static final String GSONCATEGORIA_EMPRESA = "gsoncategoria_empresa";
    private SeekBar seek_bar_distancia_delivery,seek_bar_price_delivery;

    private String select_categoria="";

    private String select_subcategoria="";

    private  ItemSearchResultsAdapter adapter;

    private List<CategoriaEmpresa> proof=new ArrayList<>();

    private  int progressDistancia=1;

    private  int progressDelivery=1;


    private ImageButton ic_back;

    private SearchView searchView;

    private RecyclerView recyclerview_resultado,recycler_categoria;

    private Index index;

    private CompletionHandler completionHandler;

    private List<Empresa> lista;

    private LinearLayout linearlayout_filtro;

    public static List<Boolean> listaCategoria;

    private CategoriaFilterResultsAdapter adapterFilter;

    private GsonCategoriaEmpresa gsonCategoriaEmpresa;

    private TextView precio_delivery_filtro,distancia_filtro;

    private Button mButton;

    private LinearLayout linearlayout_box;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        reciveData();
        initVariable();
        declararWidget();


        Client client = new Client(getString(R.string.algolia_applicationID), getString(R.string.algolia_apiKey));
        index = client.getIndex(getString(R.string.algolia_indexName));

        completionHandler = (content, error) -> {
            //System.out.println(content);
            List<Empresa> lista=convertJsonToEmpresa(String.valueOf(content));
            adapter.setResults(lista,SearchActivity.this);
            recyclerview_resultado.setAdapter(adapter);
        };



        searchOrder();

        ic_back.setOnClickListener(v->{onBackPressed();});

        incrementPriceDelivery();
        incrementPriceProducts();

        clickButtonSearch();
        setDataWidget();

        linearlayout_filtro.setOnClickListener(v->{
            searchView.clearFocus();
            searchView.setIconified(true);
            ic_back.setVisibility(View.VISIBLE);

        });
    }




    public void initVariable() {
        adapter=new ItemSearchResultsAdapter();
        adapterFilter=new CategoriaFilterResultsAdapter();
        lista=new ArrayList<>();
        listaCategoria=new ArrayList<>();

    }


    private void declararWidget(){

       /* seek_bar_distancia_delivery=view.findViewById(R.id. seek_bar_price_product);
        seek_bar_price_delivery=view.findViewById(R.id.seek_bar_price_delivery);
        mRecyclerView=view.findViewById(R.id.recycler_categoria);*/

        ic_back=findViewById(R.id.ic_back);

        searchView=findViewById(R.id.searchview);

        recyclerview_resultado=findViewById(R.id.recyclerview_resultado);
        recyclerview_resultado.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerview_resultado.setVisibility(View.GONE);

        linearlayout_filtro=findViewById(R.id.linearlayout_filtro);

        loadDataCategoriaColor(gsonCategoriaEmpresa.getListaCategoriaEmpresa().size());

        adapterFilter.setResults(gsonCategoriaEmpresa.getListaCategoriaEmpresa(),SearchActivity.this);
        recycler_categoria=findViewById(R.id.recycler_categoria);
        recycler_categoria.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        recycler_categoria.setAdapter(adapterFilter);

        seek_bar_price_delivery=findViewById(R.id.seek_bar_price_product);
        seek_bar_distancia_delivery=findViewById(R.id.seek_bar_price_delivery);

        mButton=findViewById(R.id.button_search);

        precio_delivery_filtro=findViewById(R.id.precio_delivery_filtro);

        linearlayout_box=findViewById(R.id.linearlayout_box);

         distancia_filtro=findViewById(R.id.distancia_filtro);

    }
    private void searchOrder() {


        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnSearchClickListener(v->{
           // Toast.makeText(SearchActivity.this,"estamod cerrando",Toast.LENGTH_LONG).show();
            linearlayout_box.setVisibility(View.GONE);
            ic_back.setVisibility(View.GONE);
        });

        searchView.setOnCloseListener(() -> {
           //
            // Toast.makeText(SearchActivity.this,"estamod abriendo",Toast.LENGTH_LONG).show();
            linearlayout_box.setVisibility(View.VISIBLE);

            ic_back.setVisibility(View.VISIBLE);
            linearlayout_filtro.setVisibility(View.VISIBLE);

            return false;
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                //adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                //adapter.getFilter().filter(query);
                if(query.length()>1){
                    recyclerview_resultado.setVisibility(View.VISIBLE);
                    linearlayout_filtro.setVisibility(View.GONE);
                    index.searchAsync(new Query(query), completionHandler);
                }else {
                    recyclerview_resultado.setVisibility(View.GONE);
                    linearlayout_filtro.setVisibility(View.VISIBLE);
                    //adapter.clearResults();
                }
                return false;
            }
        });

    }

    @Override
    public void clickCategoria(Empresa objeto, int position) {
        GsonEmpresa gsonEmpresa=new GsonEmpresa();
        gsonEmpresa.setListaEmpresa(lista);
        Intent intent= ResultSearchActivity.newIntent(SearchActivity.this,gsonEmpresa,objeto.getNombre_empresa(),gsonCategoriaEmpresa);
        startActivity(intent);
        finish();
    }

    private void setDataWidget(){
        String precio="S/."+progressDelivery+".00";
        precio_delivery_filtro.setText(precio);

        String distancia=progressDelivery+" km";
        distancia_filtro.setText(distancia);

    }


    private List<Empresa> convertJsonToEmpresa(String json){

        JSONObject parse ;
        JSONArray jRoutes;
        try {

            parse = new JSONObject(json);

            jRoutes=parse.getJSONArray("hits");

            lista.clear();

            for(int j=0;j<jRoutes.length();j++) {

                JSONObject empresaObject = jRoutes.getJSONObject(j);
                Empresa empresa=empresaObjectConvert(empresaObject);
                lista.add(empresa);
                // System.out.println("idempresa "+empresaObject.getJSONObject("idempresa").toString());

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lista;
    }

    private Empresa empresaObjectConvert(JSONObject jsonObject){
        Empresa empresa=new Empresa();

        try {

            empresa.setIdempresa(jsonObject.getInt("idempresa"));
            empresa.setIdsubcategoriaempresa(jsonObject.getInt("idsubcategoriaempresa"));
            empresa.setNombre_empresa(jsonObject.getString("nombre_empresa"));
            empresa.setDireccion_empresa(jsonObject.getString("direccion_empresa"));
            empresa.setRuc_empresa(jsonObject.getString("ruc_empresa"));
            empresa.setTelefono_empresa(jsonObject.getString("telefono_empresa"));
            empresa.setCelular_empresa(jsonObject.getString("celular_empresa"));
            empresa.setDescripcion_empresa(jsonObject.getString("descripcion_empresa"));
            empresa.setUrlfoto_empresa(jsonObject.getString("urlfoto_empresa"));
            empresa.setNombredueno_empresa(jsonObject.getString("nombredueno_empresa"));
            empresa.setIdcuentaempresa(jsonObject.getInt("idcuentaempresa"));
            empresa.setPorcentaje_popularidad((float)jsonObject.getDouble("idporcentaje_popularidad"));
            empresa.setCuenta_Delivery(jsonObject.getBoolean("cuenta_delivery"));
            empresa.setCosto_delivery((float) jsonObject.getDouble("costo_delivery"));
            empresa.setDetalle_delivery(jsonObject.getString("detalle_delivery"));
            empresa.setIddistrito(jsonObject.getInt("iddistrito"));
            empresa.setTiempo_aproximado_entrega(jsonObject.getString("tiempo_aproximado_entrega"));
            empresa.setIcono_empresa(jsonObject.getString("icono_empresa"));
            empresa.setHorario_inicio(jsonObject.getInt("horario_inicio"));
            empresa.setHorario_fin(jsonObject.getInt("horario_fin"));
            empresa.setTarjeta(jsonObject.getBoolean("tarjeta"));
            empresa.setDetalle_tarjeta(jsonObject.getString("detalle_tarjeta"));
            empresa.setEstrella_empresa((float) jsonObject.getDouble("estrella_empresa"));
            empresa.setMaps_coordenada_x(jsonObject.getString("maps_coordenada_x"));
            empresa.setMaps_coordenada_y(jsonObject.getString("maps_coordenada_y"));
            empresa.setDetalle_ubicacion(jsonObject.getString("detalle_ubicacion"));
            empresa.setDisponible(jsonObject.getBoolean("disponible"));
            empresa.setPrecio_menu((float) jsonObject.getDouble("precio_menu"));
            empresa.setMonto_descuento_menu((float) jsonObject.getDouble("monto_descuento_menu"));
            empresa.setIdusuariogeneral(jsonObject.getInt("idusuariogeneral"));
            empresa.setApellido(jsonObject.getString("apellido"));
            empresa.setNombre(jsonObject.getString("monto_descuento_menu"));
            empresa.setCorreo(jsonObject.getString("correo"));
            empresa.setCelular(jsonObject.getString("celular"));
            empresa.setFoto(jsonObject.getString("foto"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return empresa;
    }

    public static Intent newIntent(Context context, GsonCategoriaEmpresa gsonCategoriaEmpresa){
        Intent intent= new Intent(context, SearchActivity.class);
        intent.putExtra(GSONCATEGORIA_EMPRESA,gsonCategoriaEmpresa);
        return intent;
    }


    private  void reciveData(){
        if(getIntent().getSerializableExtra(GSONCATEGORIA_EMPRESA)!=null){
            gsonCategoriaEmpresa=(GsonCategoriaEmpresa) getIntent().getSerializableExtra(GSONCATEGORIA_EMPRESA);
        }

    }



    private void loadDataCategoriaColor(int cantidad){
        listaCategoria.add(true);
        for(int i=1;i<cantidad;i++){
            listaCategoria.add(false);
        }
    }

    private void incrementPriceProducts(){

        seek_bar_distancia_delivery.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            String distancia;


            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressDistancia=i;
                distancia=progressDistancia+" km";

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                distancia_filtro.setText(distancia);
            }
        });
    }

    private void incrementPriceDelivery(){


        seek_bar_price_delivery.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            String precio;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressDelivery=i;
                precio="S/."+progressDelivery+".00";
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                precio_delivery_filtro.setText(precio);

            }
        });
    }

    private void clickButtonSearch(){
        mButton.setOnClickListener(v->{

            int distancia=progressDistancia*1000;
            Intent intent= FiltroResultActivity.newIntent(SearchActivity.this,gsonCategoriaEmpresa,findIdCategoria(),distancia,progressDelivery);
            startActivity(intent);

            finish();
        });
    }

    private int findIdCategoria(){
        int contador=0;
        int position=0;
        for(Boolean respuesta:listaCategoria){
            if(respuesta){
                position=contador;
            }
            contador++;
        }

        CategoriaEmpresa categoriaEmpresa=gsonCategoriaEmpresa.getListaCategoriaEmpresa().get(position);


        return categoriaEmpresa.getIdCategoriaEmpresa();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
           // Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();

        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            searchView.clearFocus();
            searchView.setIconified(true);

           // Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
        }
    }


}
