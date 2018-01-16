package eus.ehu.tta.aupapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class VerEventosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_eventos);
    }

    public void verEventos(View view){

        //Funcion para crear la View con los eventos entre las fechas indicadas


        final int fechaInicial = Integer.parseInt(((EditText)findViewById(R.id.fecha_inicial)).getText().toString());
        final int fechaFinal = Integer.parseInt(((EditText)findViewById(R.id.fecha_final)).getText().toString());



    }
}
