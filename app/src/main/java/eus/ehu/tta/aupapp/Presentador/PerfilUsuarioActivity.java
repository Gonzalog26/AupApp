package eus.ehu.tta.aupapp.Presentador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import eus.ehu.tta.aupapp.R;

public class PerfilUsuarioActivity extends AppCompatActivity {


    public static String EXTRA_LOGIN;
    public static String EXTRA_NOMBRE;
    public static String EXTRA_PAPELLIDO;
    public static String EXTRA_SAPELLIDO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        String EXTRA_NOMBRE = sharedPreferences.getString("nombre","");
        String EXTRA_PAPELLIDO = sharedPreferences.getString("primerApellido","");
        String EXTRA_SAPELLIDO = sharedPreferences.getString("segundoApellido","");
        String EXTRA_LOGIN = sharedPreferences.getString("login","");


        char inicial = EXTRA_NOMBRE.charAt(0);
        inicial=(char)(inicial-32);
        EXTRA_NOMBRE=inicial+EXTRA_NOMBRE.substring(1,EXTRA_NOMBRE.length());

        inicial = EXTRA_PAPELLIDO.charAt(0);
        inicial=(char)(inicial-32);
        EXTRA_PAPELLIDO=inicial+EXTRA_PAPELLIDO.substring(1,EXTRA_PAPELLIDO.length());

        inicial = EXTRA_SAPELLIDO.charAt(0);
        inicial=(char)(inicial-32);
        EXTRA_SAPELLIDO=inicial+EXTRA_SAPELLIDO.substring(1,EXTRA_SAPELLIDO.length());


        ImageView imageView = (ImageView) findViewById(R.id.foto_perfil);
        Picasso.with(this).load("file:///storage/emulated/0/Pictures/"+EXTRA_LOGIN+".jpg").resize(1000,1000).into(imageView);

        TextView textLogin = (TextView) findViewById(R.id.frase_login);
        textLogin.setText(EXTRA_LOGIN);

        TextView textNombreApellidos = (TextView) findViewById(R.id.frase_nombre_apellidos);
        textNombreApellidos.setText(EXTRA_NOMBRE+" "+EXTRA_PAPELLIDO+" "+EXTRA_SAPELLIDO);


    }

    public void cerrarSesion(View view){

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nombre","");
        editor.putString("primerApellido","");
        editor.putString("segundoApellido","");
        editor.putString("login","");
        editor.putInt("respondidas",0);
        editor.putInt("correctas",0);
        editor.commit();

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);

    }


}
