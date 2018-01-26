package eus.ehu.tta.aupapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;

public class PerfilUsuarioActivity extends AppCompatActivity {


    public static String EXTRA_LOGIN;
    public static String EXTRA_NOMBRE;
    public static String EXTRA_PAPELLIDO;
    public static String EXTRA_SAPELLIDO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        EXTRA_LOGIN = extras.getString("EXTRA_LOGIN");
       // EXTRA_NOMBRE = extras.getString("EXTRA_NOMBRE");
       // EXTRA_PAPELLIDO = extras.getString("EXTRA_PAPELLIDO");
        EXTRA_SAPELLIDO = extras.getString("EXTRA_SAPELLIDO");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String EXTRA_NOMBRE = sharedPreferences.getString("Nombre",null);
        String EXTRA_PAPELLIDO = sharedPreferences.getString("PrimerApellido","default");


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

}
