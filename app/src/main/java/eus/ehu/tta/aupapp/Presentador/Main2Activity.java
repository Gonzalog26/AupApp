package eus.ehu.tta.aupapp.Presentador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import eus.ehu.tta.aupapp.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        String nombre = sharedPreferences.getString("nombre","");
        if(nombre.compareToIgnoreCase("")!=0){
            Button button = (Button)findViewById(R.id.boton_sesion);
            button.setVisibility(View.VISIBLE);
        }
    }

    public void mantenerSesion(View view){
        Intent intent = new Intent(this,MenuActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_LOGIN","");
        extras.putString("EXTRA_NOMBRE","");
        extras.putString("EXTRA_PAPELLIDO","");
        extras.putString("EXTRA_SAPELLIDO","");
        extras.putString("Actividad","mantenersesion");
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void registrarse(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void iniciarSesion(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
