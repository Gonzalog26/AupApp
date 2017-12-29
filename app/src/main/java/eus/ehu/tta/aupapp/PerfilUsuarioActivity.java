package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PerfilUsuarioActivity extends AppCompatActivity {


    public static String EXTRA_LOGIN;
    public static String EXTRA_NOMBRE;
    public static String EXTRA_PAPELLIDO;
    public static String EXTRA_SAPELLIDO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        //Me falta la imagen del usuario


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        EXTRA_LOGIN = extras.getString("EXTRA_LOGIN");
        EXTRA_NOMBRE = extras.getString("EXTRA_NOMBRE");
        EXTRA_PAPELLIDO = extras.getString("EXTRA_PAPELLIDO");
        EXTRA_SAPELLIDO = extras.getString("EXTRA_SAPELLIDO");

        TextView textLogin = (TextView) findViewById(R.id.frase_login);
        textLogin.setText(EXTRA_LOGIN);

        TextView textNombreApellidos = (TextView) findViewById(R.id.frase_nombre_apellidos);
        textNombreApellidos.setText(EXTRA_PAPELLIDO+" "+EXTRA_PAPELLIDO+" "+EXTRA_SAPELLIDO);


    }
}
