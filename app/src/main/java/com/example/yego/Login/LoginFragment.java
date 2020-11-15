package com.example.yego.Login;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.JwtResponse;
import com.example.yego.Repository.Modelo.UsuarioInfo;
import com.example.yego.ViewModel.Cliente_BiViewModel;
import com.example.yego.ViewModel.UsuarioInfoViewModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private CardView fragment_login_FACEBOOK,fragment_login_GMAIL;

    private CallbackManager mCallbackManager;

    private UsuarioInfoViewModel viewModel;

    private GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 9001;

    private LoadingDialog loadingDialog;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel=new ViewModelProvider(this).get(UsuarioInfoViewModel.class);
        viewModel.init();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         loadingDialog= new LoadingDialog(getActivity());

        initWidget(view);
        buttonLoginWithFacebook();
        buttonLoginWithGoogle();

        returnToken();
    }

    private void initWidget(View view){
        fragment_login_FACEBOOK= view.findViewById(R.id.fragment_login_FACEBOOK);
        fragment_login_GMAIL=view.findViewById(R.id.fragment_login_GOOGLE);

    }

    private void buttonLoginWithFacebook(){

        mCallbackManager = CallbackManager.Factory.create();
        List<String> nuevo=new ArrayList<>();
        nuevo.add("email");
        nuevo.add("public_profile");
        fragment_login_FACEBOOK.setOnClickListener( v->{
            loginWithFacebook(nuevo);
        });



    }



    private void loginWithFacebook(List<String > nuevo){

        LoginManager.getInstance().logInWithReadPermissions(this,nuevo);
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               // Log.d(TAGFACEBOOK, "facebook:onSuccess:" + loginResult);
              //  loginResult.getAccessToken()

                loaduserProfile(loginResult.getAccessToken());
                loadingDialog.startLoadingDialog();


            }

            @Override
            public void onCancel() {

                loadingDialog.startLoadingDialog();

                System.out.println("FUE CANCELADO");
            }

            @Override
            public void onError(FacebookException error) {
                loadingDialog.startLoadingDialog();

                System.out.println("encontramos erroeres" + error.getMessage());

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);



        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                loadingDialog.startLoadingDialog();
                registerOrLoginClient(account);


            } catch (ApiException e) {
                loadingDialog.startLoadingDialog();

                Toast.makeText(getContext(), "Hubo un error al iniciar sesion", Toast.LENGTH_SHORT).show();

            }
        }
    }





   /* AccessTokenTracker tokenTracker= new AccessTokenTracker(){

        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if(currentAccessToken==null){
                System.out.println("nothing in facebook data");
            }else{
                System.out.println("show facebook data");

                loaduserProfile(currentAccessToken);
            }

        }
    };*/

   private void buttonLoginWithGoogle(){
       fragment_login_GMAIL.setOnClickListener(v->{
           loginWithGoogle();
       });
   }


    private void loginWithGoogle(){

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        // [START initialize_auth]
        // Initialize Firebase Auth
        // [END initialize_auth]

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    private void registerOrLoginClient(GoogleSignInAccount account){

        UsuarioInfo usuarioInfo=new UsuarioInfo();
        usuarioInfo.setNombre(account.getDisplayName());
        usuarioInfo.setApellido(account.getFamilyName());
        usuarioInfo.setCelular("");
        usuarioInfo.setCorreo(account.getEmail());
        usuarioInfo.setFoto(account.getPhotoUrl().toString());


        //LNSERTAR LA INFORMACION DEL USUARIO SI ES NUEVO O EN CASO YA ESTE REGISTRADO POR ACA SOLO SE RETORNARA DATOS  BASICOS COMO CORREO Y EL TOKEN

        viewModel.registrarUsuarioProvider(usuarioInfo);

    }



    private void loaduserProfile(AccessToken newAccessToken){

        GraphRequest request= GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                System.out.println("rrive here");

                try {


                    UsuarioInfo usuarioInfo=new UsuarioInfo();
                    usuarioInfo.setNombre(object.getString("first_name"));
                    usuarioInfo.setApellido(object.getString("last_name"));
                    usuarioInfo.setCelular("");
                    usuarioInfo.setCorreo(object.getString("email"));
                    String id=object.getString("id");
                    String url="https://graph.facebook.com/"+id+"/picture?type=normal";
                    usuarioInfo.setFoto(url);


                    //LNSERTAR LA INFORMACION DEL USUARIO SI ES NUEVO O EN CASO YA ESTE REGISTRADO POR ACA SOLO SE RETORNARA DATOS  BASICOS COMO CORREO Y EL TOKEN

                    viewModel.registrarUsuarioProvider(usuarioInfo);


                } catch (JSONException e) {
                    loadingDialog.startLoadingDialog();

                    System.out.println("print error");
                    e.printStackTrace();
                }


            }
        });
        Bundle parameters= new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }


    private void returnToken(){
        viewModel.getJwtResponseLiveData().observe(getViewLifecycleOwner(), new Observer<JwtResponse>() {
            @Override
            public void onChanged(JwtResponse jwtResponse) {

                if(jwtResponse!=null){


                    SessionPrefs.get(getContext()).saveJwtResponse(jwtResponse);

                    if(jwtResponse.getIdubicacion()==0){

                        //IR A UBICACION PARA REGISTRAR UN PUNTO DE REFERENCIA

                        loadingDialog.dismissDialog();
                        Intent intent= LocationActivity.newIntentLocationActivity(getContext(),true);
                        startActivity(intent);
                        getActivity().finish();


                    }else{
                        //IR DIRECTAMENTE A HOME

                        loadingDialog.dismissDialog();

                        Intent intent=AfterLoginActivity.newIntentLocationActivity(getContext(),jwtResponse.getId().intValue());
                        startActivity(intent);
                        getActivity().finish();
                    }



                }else{
                    loadingDialog.dismissDialog();

                    Toast.makeText(getContext(), "Intentarlo otra vez porfavor ,probelmas en la red", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    }


