package eus.ehu.tta.aupapp.Presentador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eus.ehu.tta.aupapp.R;

public class EventosMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_menu2);
    }

    public void crearEvento(View view){
        Intent intent = new Intent(this,CrearEventoActivity.class);
        startActivity(intent);
    }

    public void verEventos(View view){
        Intent intent = new Intent(this, VerEventosActivity.class);
        startActivity(intent);
    }

    public void atras(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
