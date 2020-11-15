package com.example.yego.Login;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.JwtResponse;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Modelo.UsuarioInfo;
import com.example.yego.View.InicioActivity;
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

public class IniciarSesionFragment extends Fragment {


    private LinearLayout fragment_login_FACEBOOK,fragment_login_GMAIL,progress_bar;

    private CallbackManager mCallbackManager;

    private UsuarioInfoViewModel viewModel;

    private GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 9001;

    private Cliente_BiViewModel viewModelCliente;


    public IniciarSesionFragment() {
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
        return inflater.inflate(R.layout.fragment_iniciar_sesion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initWidget(view);
        //clickbuttonLogin();
        //loadingDialog= new LoadingDialog(getActivity());

        initWidget(view);
        buttonLoginWithFacebook();
        buttonLoginWithGoogle();

        returnToken();
    }



    /*private void initWidget(View view){
        fragment_iniciar_sesion_LOGIN= view.findViewById(R.id.fragment_iniciar_sesion_LOGIN);
        fragment_iniciar_sesion_REGISTRARSE=view.findViewById(R.id.fragment_iniciar_sesion_REGISTRARSE);

    }


    private void clickbuttonLogin(){

        fragment_iniciar_sesion_LOGIN.setOnClickListener( v->{
            NavController nav= Navigation.findNavController(v);
            nav.navigate(R.id.loginFragment);
        });
    }*/

    private void initWidget(View view){
        fragment_login_FACEBOOK= view.findViewById(R.id.fragment_login_FACEBOOK);
        fragment_login_GMAIL=view.findViewById(R.id.fragment_login_GOOGLE);
        progress_bar=view.findViewById(R.id.progress_bar);

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
                progress_bar.setVisibility(View.VISIBLE);


            }

            @Override
            public void onCancel() {

                progress_bar.setVisibility(View.GONE);

                System.out.println("FUE CANCELADO");
            }

            @Override
            public void onError(FacebookException error) {
                progress_bar.setVisibility(View.GONE);

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
                progress_bar.setVisibility(View.VISIBLE);

                registerOrLoginClient(account);


                mGoogleSignInClient.revokeAccess();

            } catch (ApiException e) {
                progress_bar.setVisibility(View.GONE);

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
                    progress_bar.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Lo sentimos no pudo ingresar", Toast.LENGTH_SHORT).show();
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

                       /* Intent intent= LocationActivity.newIntentLocationActivity(getContext(),true);*/
                        Intent intent=new Intent(getContext(),PhoneNumberActivity.class);
                        startActivity(intent);
                        getActivity().finish();


                    }else{
                        //IR DIRECTAMENTE A HOME

                        getDataCLiente_Bi(jwtResponse.getId().intValue());

                        /*Intent intent=AfterLoginActivity.newIntentLocationActivity(getContext(),jwtResponse.getId().intValue());
                        startActivity(intent);
                        getActivity().finish();*/
                    }



                }else{
                    progress_bar.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Intentarlo otra vez porfavor ,probelmas en la red", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void getDataCLiente_Bi(int idusuario){

        viewModelCliente=new ViewModelProvider(this).get(Cliente_BiViewModel.class);
        viewModelCliente.init();
        viewModelCliente.findInformacionClienteBi(idusuario);

        viewModelCliente.getCliente_biLiveData().observe(this, new Observer<Cliente_Bi>() {
            @Override
            public void onChanged(Cliente_Bi cliente_bi) {

                if(cliente_bi!=null){

                    Ubicacion.ubicacionEnable=new Ubicacion(cliente_bi.getIdubicacion(),cliente_bi.getUbicacion_nombre(),cliente_bi.getUbicacion_comentarios(),cliente_bi.getIdusuario(),true,false,"",cliente_bi.getMaps_detalle(),cliente_bi.getMaps_coordenada_x(),cliente_bi.getMaps_coordenada_y());

                    // Usuario.idUsuario=cliente_bi.getIdusuario();


                    Cliente_Bi.registrarCliente_bi(cliente_bi);


                    //IR AL HOME
                    Intent intent= InicioActivity.newIntentInicioActivity(getContext(),false);
                    startActivity(intent);
                    requireActivity().finish();

                }else{

                    progress_bar.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Experimentamos problemas en nuestros servicios", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
