package com.example.yego.View.HorarioUI;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Horario;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class
HorarioListResultsAdapter extends  RecyclerView.Adapter<HorarioListResultsAdapter.HorarioListHolder> {

    List<Horario> results= new ArrayList<>();
    ClickHorario mClickHorario;

    @NonNull
    @Override
    public HorarioListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horario,parent,false);
        return new HorarioListResultsAdapter.HorarioListHolder(view,mClickHorario);

    }

    @Override
    public void onBindViewHolder(@NonNull HorarioListHolder holder, int position) {

        Horario horario=results.get(position);
        holder.activity_horario_NOMBRE_HORARIO.setText(horario.getHorario_nombre());

        if(position==0) {
            holder.activity_horario_NOMBRE_ENABLE.setText("Ahora");
        }

        holder.enable.setCardElevation(4);
        holder.enable.setCardBackgroundColor(Color.rgb(154, 217, 163));
        holder.activity_horario_NOMBRE_ENABLE.setTextColor(Color.rgb(50, 90, 115));
        holder.activity_horario_NOMBRE_HORARIO.setTextColor(Color.rgb(50, 90, 115));

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Horario> results,ClickHorario mClickHorario){
        this.results=results;
        notifyDataSetChanged();
        this.mClickHorario=mClickHorario;
    }

    public class HorarioListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView activity_horario_NOMBRE_HORARIO,activity_horario_NOMBRE_ENABLE;
        LinearLayout item_select;
        CardView enable;
        ImageView enable_time;
        ClickHorario mClickHorario;
        public HorarioListHolder(@NonNull View itemView,ClickHorario mClickHorario) {
            super(itemView);
            item_select=itemView.findViewById(R.id.item_select);
            activity_horario_NOMBRE_HORARIO=itemView.findViewById(R.id.activity_horario_NOMBRE_HORARIO);
            activity_horario_NOMBRE_ENABLE=itemView.findViewById(R.id.activity_horario_NOMBRE_ENABLE);
            enable=itemView.findViewById(R.id.enable);
            enable_time=itemView.findViewById(R.id.enable_time);
            this.mClickHorario=mClickHorario;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHorario.clickItem(results.get(getAdapterPosition()));
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);

    }

    public interface ClickHorario{
        void clickItem(Horario horario);
    }
}
