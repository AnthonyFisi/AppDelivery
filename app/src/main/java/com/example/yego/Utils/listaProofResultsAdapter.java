package com.example.yego.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yego.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class listaProofResultsAdapter extends  RecyclerView.Adapter<listaProofResultsAdapter.listaProofResultHolder> {
    List<String> results= new ArrayList<>();

    @NonNull
    @Override
    public listaProofResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proof,parent,false);

        return new  listaProofResultsAdapter.listaProofResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listaProofResultHolder holder, int position) {
        String data=results.get(position);
        holder.text_proof.setText(data);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<String> results){
        this.results=results;
        notifyDataSetChanged();
    }

    public class listaProofResultHolder extends RecyclerView.ViewHolder {

        private TextView text_proof;

        public listaProofResultHolder(@NonNull View itemView) {
            super(itemView);
            text_proof=itemView.findViewById(R.id.text_proof);
        }
    }
}
