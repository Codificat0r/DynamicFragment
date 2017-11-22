package com.example.dynamicfragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements FragmentA.FragmentAListener {

    private Fragment fragmenta;
    private Fragment fragmentb;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private final String TAG = "fragmenta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        //Con el tag que solo se le puede poner en el add de la transaccion comprobamos que no vuelva a ejecutar la transaccion
        //para que vuelva y vuelva a poner mas fragments encima uno de otro al girar la pantalla.
        if (fragmentManager.findFragmentByTag(TAG) == null) {
            fragmenta = new FragmentA();
            //Si le tuviesemos que pasar argumentos al fragmentA le debemos poner la linea justo despues de inicializarlo,
            //ya que no pueden pasarse argumentos dentro de la transaccion. Si no se pone justo despues de la inicializacion,
            //no se le pasan los argumentos.
            fragmentTransaction = fragmentManager.beginTransaction();
            //Solo se puede añadir un fragment y despues vamos reemplazando con otros fragment.
            fragmentTransaction.add(android.R.id.content, fragmenta, TAG);
            fragmentTransaction.commit();
            Log.d(TAG, "Activity: onCreate()");
        }
    }

    @Override
    public void onFragmentAEvent(String message, int size) {
        //fragmentb = new FragmentB();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putInt("size", size);
        //Con el metodo setArguments se pasa la información que necesita el fragment. Se debe poner justo despues de la
        //inicializacion, por lo que nos aseguramos que siempre lo pasaremos despues de la inicializacion realizando
        //el patrón factory.
        //fragmentb.setArguments(bundle);
        //Se debe usar el patrón factorio, donde la creación del objeto y el paso de argumentos se ejecuten
        //uno detrás de otro, consecutivamente.
        fragmentb = FragmentB.newInstance(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragmentb);
        //antes de realizar el commit se debe guardar la transaccion, para que al girar siga igual. Se guarda el
        //estado de ese fragment y esa activity en ese momento. Debe ser justo antes del commit. Pero cuando damos
        //al boton back podemos volver a la activity con el fragment a y al girar no vuelve a cargar este estado guardado
        //porque de esta pila de las transacciones se ha eliminado el fragment b automaticamente al darle al back
        //que destruye el fragment b.
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Activity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Activity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity: onDestroy()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Activity: onStart()");
    }
}
