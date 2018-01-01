package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CrearEventoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);
    }

    public void crearEvento(View view){

        //Funciones necesarias para crear el evento


        //Una vez creado el evento se vuelve al menu inicial
        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
    }

    public void sacarFoto(View view){

        //Funciones necesarias para sacar una foto

    }
}
