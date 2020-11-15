package com.example.yego.Utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ComnetarioViewModel extends ViewModel {

    public MutableLiveData<CharSequence>  comentario=new MutableLiveData<>();


    public void setComentario(CharSequence contenido){
        comentario.setValue(contenido);
    }

    public LiveData<CharSequence> getComentario(){
        return comentario;
    }

}
