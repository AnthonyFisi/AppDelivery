package com.example.yego.View.MethodPayUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Envio_empresa;
import com.example.yego.Repository.Modelo.TipoPago;
import com.example.yego.View.CarritoUI.Envio_empresa.Envio_empresaFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListaTypesPayResultsAdapter extends RecyclerView.Adapter<ListaTypesPayResultsAdapter.ListaTypesPayResultsHolder> {

    private List<TipoPago> results = new ArrayList<>();
    private ClickTipoPago mClickTipoPago;

    @NonNull
    @Override
    public ListaTypesPayResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_pay,parent,false);
        return new ListaTypesPayResultsAdapter.ListaTypesPayResultsHolder(view,mClickTipoPago);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaTypesPayResultsHolder holder, int position) {

        TipoPago tipoPago=results.get(position);
        System.out.println("estoy item"+tipoPago.getTipopago_nombre());
        //holder.item_type_pay_NOMBRE_PAGO.setText(tipoPago.getTipopago_nombre());
        String imageUrl = tipoPago.getTipopago_url()
                .replace("http://", "https://");
        Glide.with(holder.itemView)
                .load(imageUrl)
                .into(holder.item_type_pay_ICON);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<TipoPago> results , ClickTipoPago clickTipoPago){
        System.out.println("Estoy dentro" +results.size());
        this.results=results;
        this.mClickTipoPago=clickTipoPago;
        notifyDataSetChanged();
    }



    public class ListaTypesPayResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ClickTipoPago mClickTipoPago;
        ImageView item_type_pay_ICON;
      //  TextView item_type_pay_NOMBRE_PAGO;

        public ListaTypesPayResultsHolder(@NonNull View itemView,ClickTipoPago clickTipoPago) {
            super(itemView);
       //     item_type_pay_NOMBRE_PAGO=itemView.findViewById(R.id.item_type_pay_NOMBRE_PAGO);
            item_type_pay_ICON=itemView.findViewById(R.id.item_type_pay_ICON);
            this.mClickTipoPago=clickTipoPago;



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickTipoPago.clickPago(results.get(getAdapterPosition()));
        }
    }


    public interface ClickTipoPago{
        void clickPago(TipoPago tipoPago);
    }
}
