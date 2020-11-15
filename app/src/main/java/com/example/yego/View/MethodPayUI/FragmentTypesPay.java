package com.example.yego.View.MethodPayUI;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonTipoPago;
import com.example.yego.Repository.Modelo.TipoPago;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.Repository.Service.TipoPagoService;
import com.example.yego.View.CarritoUI.FragmentListaCarrito;
import com.example.yego.ViewModel.TipoPagoViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTypesPay extends AppCompatDialogFragment implements  ListaTypesPayResultsAdapter.ClickTipoPago{

    public static final String TAG = "typepay";
    private static final String VENTA = "venta";
    private static final String EMPRESA = "empresa";
    private TipoPagoViewModel viewModel;
    private ListaTypesPayResultsAdapter adapter;
    private OnDataPass dataPasser;
    private Empresa empresa;
    private Venta venta;
    RecyclerView recyclerView;
    private ImageButton close_comentario;

    private ProgressBar progress_bar_pago;

    public FragmentTypesPay() {
        // Required empty public constructor
    }

    public static FragmentTypesPay newInstance(Venta venta,Empresa empresa){
        FragmentTypesPay fragment=new FragmentTypesPay();
        Bundle bundle=new Bundle();
        bundle.putSerializable(VENTA,venta);
        bundle.putSerializable(EMPRESA,empresa);
        fragment.setArguments(bundle);
        return  fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            empresa=(Empresa) getArguments().getSerializable(EMPRESA);
            venta=(Venta) getArguments().getSerializable(VENTA);

        }


        adapter=new ListaTypesPayResultsAdapter();
        viewModel = new ViewModelProvider(this).get(TipoPagoViewModel.class);
        viewModel.init();
        viewModel.getGsonTipoPagoLiveData().observe(this, new Observer<GsonTipoPago>() {
            @Override
            public void onChanged(GsonTipoPago gsonTipoPago) {

                progress_bar_pago.setVisibility(View.GONE);
                if(gsonTipoPago.getListaTipoPago()!=null){

                    adapter.setResults(gsonTipoPago.getListaTipoPago(),FragmentTypesPay.this);

                }else{

                    Toast.makeText(getContext(),"Volver a intentarlo ",Toast.LENGTH_LONG).show();
                }
            }
        });


    }




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.fragment_types_pay, null);

        setView(content);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(content);

        AlertDialog alertDialog = builder.create();
        //asegura que se muestre el teclado
        alertDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return alertDialog;
    }


    public void setView(View view) {
        close_comentario=view.findViewById(R.id.close_comentario);
        recyclerView=view.findViewById(R.id.fragment_types_pay_RECYCLERVIEW);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        viewModel.searchListTipoPagoEnable();
        progress_bar_pago=view.findViewById(R.id.progress_bar_pago);

        close();
    }



    private  void close(){

        close_comentario.setOnClickListener( v-> dismiss());


    }

    @Override
    public void clickPago(TipoPago tipoPago) {

        passData(tipoPago);

        dismiss();
    }


    public interface OnDataPass {
        void onDataPass(TipoPago tipoPago);
    }

    private void passData(TipoPago tipoPago) {
        dataPasser.onDataPass(tipoPago);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        dataPasser = (FragmentTypesPay.OnDataPass) context;
    }

}
