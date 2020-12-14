package com.example.yego.View.SearchUI;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.CategoriaEmpresa;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.View.SearchUI.ResultSearchUI.ResultSearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements ItemSearchResultsAdapter.Filtro{

    private SeekBar seek_bar_distancia_delivery,seek_bar_price_delivery;

    private String select_categoria="";

    private String select_subcategoria="";

    private  ItemSearchResultsAdapter adapter;

    private RecyclerView mRecyclerView;

    private List<CategoriaEmpresa> proof=new ArrayList<>();

  //  private TextView precio_delivery_filtro,distancia_filtro;


    private  int progressDistancia=1;

    private  int progressDelivery=1;


    private ImageButton ic_back;

    private SearchView searchView;

    private RecyclerView recyclerview_resultado;


    private Index index;

    private CompletionHandler completionHandler;

    private List<Empresa> lista;

    private LinearLayout linearlayout_filtro;

    private List<Boolean> listaCategoria;

    private CategoriaFilterResultsAdapter adapterFilter;

    private LinearLayout linearlayout_box;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new ItemSearchResultsAdapter();
        adapterFilter=new CategoriaFilterResultsAdapter();
        lista=new ArrayList<>();
        listaCategoria=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        declararWidget(view);


        Client client = new Client("3Y4G1MKC6Q", "c7929e113fd34b58dc374e0608cbfb66");
        index = client.getIndex("yegosearch");

        completionHandler = (content, error) -> {
            //System.out.println(content);
            List<Empresa> lista=convertJsonToEmpresa(String.valueOf(content));
            adapter.setResults(lista,SearchFragment.this);
            recyclerview_resultado.setAdapter(adapter);
        };



        searchOrder();
    }



    private void declararWidget(View view){

       /* seek_bar_distancia_delivery=view.findViewById(R.id. seek_bar_price_product);
        seek_bar_price_delivery=view.findViewById(R.id.seek_bar_price_delivery);
        mRecyclerView=view.findViewById(R.id.recycler_categoria);*/

        ic_back=view.findViewById(R.id.ic_back);

        searchView=view.findViewById(R.id.searchview);

        recyclerview_resultado=view.findViewById(R.id.recyclerview_resultado);
        recyclerview_resultado.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerview_resultado.setVisibility(View.GONE);

        linearlayout_filtro=view.findViewById(R.id.linearlayout_filtro);

        linearlayout_box= view.findViewById(R.id.linearlayout_box);

    }
    private void searchOrder() {


        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnSearchClickListener(v->{
            Toast.makeText(getContext(),"estamod cerrando",Toast.LENGTH_LONG).show();
            linearlayout_box.setVisibility(View.GONE);
            ic_back.setVisibility(View.GONE);
        });

        searchView.setOnCloseListener(() -> {
            Toast.makeText(getContext(),"estamod abriendo",Toast.LENGTH_LONG).show();
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
                    //index.searchAsync(new Query(query), completionHandler);
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
        //Intent intent= ResultSearchActivity.newIntent(getContext(),gsonEmpresa,objeto.getNombre_empresa(),cate);
       // startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
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
}
