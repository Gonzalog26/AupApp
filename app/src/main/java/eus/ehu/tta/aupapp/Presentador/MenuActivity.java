package eus.ehu.tta.aupapp.Presentador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eus.ehu.tta.aupapp.R;

public class MenuActivity extends AppCompatActivity {

    public static String EXTRA_LOGIN;
    public static String EXTRA_NOMBRE;
    public static String EXTRA_PAPELLIDO;
    public static String EXTRA_SAPELLIDO;
    public static String Actividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Actividad = extras.getString("Actividad");


        if(Actividad.compareToIgnoreCase("Registro")==0 || Actividad.compareToIgnoreCase("login")==0){

            EXTRA_LOGIN = extras.getString("EXTRA_LOGIN");
            EXTRA_NOMBRE = extras.getString("EXTRA_NOMBRE");
            EXTRA_PAPELLIDO = extras.getString("EXTRA_PAPELLIDO");
            EXTRA_SAPELLIDO = extras.getString("EXTRA_SAPELLIDO");


            SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("nombre",EXTRA_NOMBRE);
            editor.putString("primerApellido",EXTRA_PAPELLIDO);
            editor.putString("segundoApellido",EXTRA_SAPELLIDO);
            editor.putString("login",EXTRA_LOGIN);
            editor.putInt("respondidas",0);
            editor.putInt("correctas",0);
            editor.commit();
        }



    }

    @Override
    public void onBackPressed(){

    }

    public void perfilUsuario(View view){
        Intent intent = new Intent(this, PerfilUsuarioActivity.class);
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
