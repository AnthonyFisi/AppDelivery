package com.example.yego.View.OrdenUI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Gson.GsonOrden;
import com.example.yego.Repository.Modelo.Gson.OrdenGeneralGson;
import com.example.yego.Repository.Modelo.Gson.Orden_estado_restauranteGson;
import com.example.yego.Repository.Modelo.Gson.RepartidorInformationGson;
import com.example.yego.Repository.Modelo.Orden_estado_general;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.View.InicioActivity;
import com.example.yego.View.ui.Perfil.PerfilFragment;
import com.example.yego.ViewModel.OrdenViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;

public class FragmentListOrden extends Fragment implements  ListOrdenResultsAdapter.ClickOrden{

    private ListOrdenResultsAdapter adapter;

    private OrdenViewModel viewModel;


    private NavController mNavController;

    private LinearLayout empty_ordenes;

    private RecyclerView recyclerView;

    private ProgressBar progress_bar;

    private ImageView ic_back;

    private BackToInicio mBackToInicio;


    public static FragmentListOrden newInstance(String param1, String param2) {
        FragmentListOrden fragment = new FragmentListOrden();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter= new ListOrdenResultsAdapter();
        viewModel = ViewModelProviders.of(this).get(OrdenViewModel.class);
        viewModel.init();
        viewModel.getOrdenDisponibleLiveData().observe(this, new Observer<OrdenGeneralGson>() {
            @Override
            public void onChanged(OrdenGeneralGson ordenGeneralGson) {
                System.out.println("NO VUELVE EL ERROR EOQ E");
                progress_bar.setVisibility(View.GONE);

                if(ordenGeneralGson !=null){


                    if(ordenGeneralGson.getLista()!=null){

                        adapter.setResults(ordenGeneralGson.getLista(),FragmentListOrden.this);

                    }else {
                        recyclerView.setVisibility(View.GONE);

                        empty_ordenes.setVisibility(View.VISIBLE);

                    }

                }else {
                    recyclerView.setVisibility(View.GONE);

                    empty_ordenes.setVisibility(View.VISIBLE);

                }
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_orden, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNavController= Navigation.findNavController(view);

        empty_ordenes=view.findViewById(R.id.empty_ordenes);

        progress_bar=view.findViewById(R.id.progress_bar);

        ic_back=view.findViewById(R.id.ic_back);

        viewModel.searchListOrdenDisponible(Cliente_Bi.getCliente().getIdusuario());
        recyclerView=view.findViewById(R.id.fragment_list_orde_RECYCLERVIEW);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        funPusher();

        funPusherRepartidor();

        back();
    }

    private void funPusher(){
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



                            int idventa=object.getListaOrden_estado_general().get(0).getIdventa();

                            //ACTUALIZAMOS LA LISTA CON LA CONFIRMACION O CANCELACION DEL PEDIDO QUE REALIZAMOS

                            adapter.modifiedListWithPushAction(idventa,object.getListaOrden_estado_general());




                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }

        });
        InicioActivity.pusher.connect();

        //InicioActivity.pusher.
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

                            //orden.setUsuario(object.getUsuario_general());
                            adapter.modifiedListWithPushActionWithRepartidorInformation(object.getIdventa(),object.getUsuario_general());
                            //setDataRepartidor();

                            //listenChangeLocation();


                        }catch (Exception e){

                            System.out.println(e.getMessage());
                        }
                    }
                });
            }

        });
        InicioActivity.pusher.connect();
    }



    @Override
    public void clickOrdenDetail(GsonOrden orden) {
        boolean cancelado=false;



        for(Orden_estado_general estado:orden.getLista_orden_general()){
            if(estado.getIdestadogeneral()==5){
                cancelado=true;
                Toast.makeText(getContext(),"Lo sentimos su orden fue cancelada",Toast.LENGTH_LONG).show();
                //FragmentListOrdenDirections
            }
        }

        if(!cancelado){

           // FragmentListOrdenDirections.ActionFragmentListOrdenToFragmentSupervisarOrden action=FragmentListOrdenDirections.actionFragmentListOrdenToFragmentSupervisarOrden(orden);
            //mNavController.navigate(action);

            FragmentListOrdenDirections.ActionFragmentListOrdenToDetalleOrdenFragment action=FragmentListOrdenDirections.actionFragmentListOrdenToDetalleOrdenFragment(orden);
            mNavController.navigate(action);

          //  Intent intent=SupervisarOrdenActivity.newIntent(getContext(),orden);
           // startActivity(intent);
        }
    }

    private void back(){
        ic_back.setOnClickListener(v->{

            backToInicio();
        });
    }

    private void backToInicio() {
        mBackToInicio.back();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mBackToInicio = (FragmentListOrden.BackToInicio) context;
    }

    public interface BackToInicio{
        void  back();
    }

}
