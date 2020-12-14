package com.example.yego.View.SearchUI.ResultSearchUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonCategoriaEmpresa;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.View.DetailSubCategoriaEmpresaActivity;
import com.example.yego.View.EmpresaDetailActivity;
import com.example.yego.View.SearchUI.SearchActivity;
import com.example.yego.View.SubCategoriaEmpresaUI.EmpresaResultsAdapter;
import com.example.yego.View.SubCategoriaEmpresaUI.EmpresaSortResultsAdapter;
import com.example.yego.View.SubCategoriaEmpresaUI.FragmentEmpresaSort;

import java.io.Serializable;

public class ResultSearchActivity extends AppCompatActivity implements EmpresaSortResultsAdapter.ImageListener{

    private static final String GSON_EMPRESA = "categoria_empresa";
    private static final String KEY_WORD ="key_word" ;
    private static final String GSONCATEGORIA_EMPRESA = "gsoncategoria_empresa";

    private GsonEmpresa gsonEmpresa;

    private TextView nombre_producto,cantidad_resultado;

    private RecyclerView recyclerView_resultado;

    private Serializable keyword;

    private EmpresaSortResultsAdapter adapter;

    private GsonCategoriaEmpresa gsonCategoriaEmpresa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);

        Toolbar toolbar=findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(v->{
            onBackPressed();
        });
        reciveData();
        declararWidgets();
        setDataWidget();

    }

    private void declararWidgets(){
        nombre_producto=findViewById(R.id.nombre_producto);
        cantidad_resultado=findViewById(R.id.cantidad_resultado);
        recyclerView_resultado=findViewById(R.id.recyclerView_resultado);

        adapter=new EmpresaSortResultsAdapter();
    }

    private void setDataWidget(){
        String keyword_complete="Resultado de "+keyword;
        nombre_producto.setText(keyword_complete);

        cantidad_resultado.setText(String.valueOf(gsonEmpresa.getListaEmpresa().size()));

        recyclerView_resultado.setLayoutManager(new LinearLayoutManager(ResultSearchActivity.this, LinearLayoutManager.VERTICAL, false));

        adapter.setEmpresaSortAdpater(gsonEmpresa.getListaEmpresa(),ResultSearchActivity.this);

        recyclerView_resultado.setAdapter(adapter);
    }


    public static Intent newIntent(Context context, GsonEmpresa gsonEmpresa,String keyword, GsonCategoriaEmpresa gsonCategoriaEmpresa){
        Intent intent= new Intent(context, ResultSearchActivity.class);
        intent.putExtra(GSON_EMPRESA,gsonEmpresa);
        intent.putExtra(KEY_WORD,keyword);
        intent.putExtra(GSONCATEGORIA_EMPRESA,gsonCategoriaEmpresa);

        return intent;
    }


    private  void reciveData(){
        if(getIntent().getSerializableExtra(GSON_EMPRESA)!=null){
            gsonEmpresa=(GsonEmpresa) getIntent().getSerializableExtra(GSON_EMPRESA);
        }

        if(getIntent().getSerializableExtra(GSONCATEGORIA_EMPRESA)!=null){
            gsonCategoriaEmpresa=(GsonCategoriaEmpresa) getIntent().getSerializableExtra(GSONCATEGORIA_EMPRESA);
        }
        keyword=getIntent().getSerializableExtra(KEY_WORD);

    }

    @Override
    public void imageClick(Empresa empresa) {
        Intent intent= EmpresaDetailActivity.newIntentEmpresa(ResultSearchActivity.this,empresa);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater= getMenuInflater();

        menuInflater.inflate(R.menu.menu_subcategoria,menu);

        MenuItem menuItem=menu.findItem(R.id.menu_share);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent intent=SearchActivity.newIntent(ResultSearchActivity.this,gsonCategoriaEmpresa);
                startActivity(intent);
                finish();

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }
}
