package com.example.yego.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.yego.R;

public class LoadingDialog {

    Activity mActivity;
    AlertDialog mAlertDialog;

    public LoadingDialog(Activity mActivity){
        this.mActivity=mActivity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
        LayoutInflater inflater= mActivity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_general,null));

        mAlertDialog=builder.create();
        mAlertDialog.show();
    }

    public void dismissDialog(){
        mAlertDialog.dismiss();
    }
}
