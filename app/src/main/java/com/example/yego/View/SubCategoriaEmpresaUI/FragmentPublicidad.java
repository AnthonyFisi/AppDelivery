package com.example.yego.View.SubCategoriaEmpresaUI;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Gson.GsonPublicidad;
import com.example.yego.ViewModel.PublicidadViewModel;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPublicidad extends Fragment {

    private PublicidadViewModel viewModel;
    PublicidadResultsAdapter adapter;

    public FragmentPublicidad() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Bundle bundle = getArguments();
        if(bundle != null){
            idCategoria=bundle.getInt(SubCategoriaEmpresaActivity.ARGUMENTOS_ID_CATEGORIA,200);
        }else{
            idCategoria=1;
        }*/
        //
        adapter = new PublicidadResultsAdapter(getContext());

        // viewModel = ViewModelProviders.of(this).get(BookSearchViewModel.class);
        viewModel = ViewModelProviders.of(this).get(PublicidadViewModel.class);
        viewModel.init();
        viewModel.getListaPublicidadLiveData().observe(this, new Observer<GsonPublicidad>() {
            @Override
            public void onChanged(GsonPublicidad gsonPublicidad) {
                if(gsonPublicidad!=null){
                    adapter.renewItems(gsonPublicidad.getListaPublicidad());
                }
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_publicidad, container, false);
        viewModel.searchListaPublicidad();
        SliderView sliderView = view.findViewById(R.id.imageSlider);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        return view;
    }

}
