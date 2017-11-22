package com.example.dynamicfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by usuario on 16/11/17.
 */

public class FragmentB extends Fragment {
    private TextView txvMessage;
    //Para ver un ejemplo del setRetainInstance, que guarda las variables que no sean vistas que es lo unico que se borran,
    //ponemos el setRetainInstance para que guarde las variable que no sean vistas (algunas como el seekBar si se guardan):
    private String message;
    private int size;

    //No necesita comunicacion por lo que no necesita contrato, simplemente recibira los datos del otro fragment a traves de
    //la activity. Solo implementa el onCreateView para que infle la vista que va a mostrar.

    //Patrón factory, simplificación del patrón CREATOR o BUILDER. Se suele usar este patron en los cuadros de diálogo.
    public static Fragment newInstance(Bundle bundle) {
        FragmentB fragmentB = new FragmentB();
        if (bundle != null) {
            fragmentB.setArguments(bundle);
        }
        return fragmentB;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Para que el estado dinamico de un fragment sea permanente ante un cambio de configuracion usar setRetainInstance(true);
        setRetainInstance(true);
        Log.d("onCreateB", "FragmentB onCreate");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Aqui obtenemos los argumentos, no getBundle.
        Bundle bundle = getArguments();
        if (savedInstanceState == null) { //Si viene de la primera creacion
            if (bundle != null) {
                //Guardamos en las variables que se guardaran por el setRetainInstance a true
                message = bundle.getString("message");
                size = bundle.getInt("size");
            }
        }
        //Siempre cargamos los mensajes y el tamaño
        changeTextAndSize(bundle.getString("message"), bundle.getInt("size"));
        Log.d("onCreateB", "FragmentB onViewCreated");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentb, container, false);
        txvMessage = view.findViewById(R.id.txvMessage);
        Log.d("onCreateViewB", "FragmentB onCreateView");
        return view;
    }

    public void changeTextAndSize(String message, int size) {
        txvMessage.setText(message);
        txvMessage.setTextSize(size);
    }

    //Guardamos el estado dinámico para que al girar la pantalla no se borren los datos al destruirse y crearse de nuevo.
    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("message", txvMessage.getText().toString());
        outState.putFloat("size", txvMessage.getTextSize() / getResources().getDisplayMetrics().scaledDensity);
    }

    //Restauramos el estado dinamico, o tambien se puede hacer en onViewCreated.
    //Aumentan las dimensiones automaticamente, por lo que hay que arreglarlo: https://stackoverflow.com/questions/22935490/orientation-change-increases-textviews-text-size
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            txvMessage.setText(savedInstanceState.getString("message"));
            txvMessage.setTextSize(savedInstanceState.getFloat("size"));
        }
        Log.d("onActivityCreatedB", "FragmentB onActivityCreated");
    }*/

    //MENSAJES DE LOG DE TODOS LOS ESTADOS


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("onStartB", "FragmentB onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("onStartB", "FragmentB onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResumeB", "FragmentB onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPauseB", "FragmentB onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("onStopB", "FragmentB onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onDestroyViewB", "FragmentB onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("onDestroyB", "FragmentB onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("onDetachB", "FragmentB onDetach");
    }


}
