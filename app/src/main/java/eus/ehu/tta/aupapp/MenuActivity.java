package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    public static String EXTRA_LOGIN;
    public static String EXTRA_NOMBRE;
    public static String EXTRA_PAPELLIDO;
    public static String EXTRA_SAPELLIDO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        EXTRA_LOGIN = extras.getString("EXTRA_LOGIN");
        EXTRA_NOMBRE = extras.getString("EXTRA_NOMBRE");
        EXTRA_PAPELLIDO = extras.getString("EXTRA_PAPELLIDO");
        EXTRA_SAPELLIDO = extras.getString("EXTRA_SAPELLIDO");
    }

    public void perfilUsuario(View view){
        Intent intent = new Intent(this, PerfilUsuarioActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_LOGIN",EXTRA_LOGIN);
        extras.putString("EXTRA_NOMBRE",EXTRA_NOMBRE);
        extras.putString("EXTRA_PAPELLIDO",EXTRA_PAPELLIDO);
        extras.putString("EXTRA_SAPELLIDO",EXTRA_SAPELLIDO);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void crearVerEventos(View view){
        Intent intent = new Intent(this, EventosMenuActivity.class);
        startActivity(intent);
    }

    public void diaDia(View view){
        Intent intent = new Intent(this, DiaDiaActivity.class);
        startActivity(intent);
    }

    public void conoceBizkaia(View view){
        Intent intent = new Intent(this, ConoceBizkaiaActivity.class);
        startActivity(intent);
    }

}
