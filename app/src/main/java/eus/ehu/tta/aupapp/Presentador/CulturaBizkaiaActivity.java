package eus.ehu.tta.aupapp.Presentador;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eus.ehu.tta.aupapp.R;

public class CulturaBizkaiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultura_bizkaia);
    }

    public void enlaceBailes(View view){
        Uri uriUrl = Uri.parse("https://turismo.euskadi.eus/aa30-12377/es/contenidos/informacion/aa30_cultura_vasca/es_def/danzas_y_Musica_Vasca.html");
        Intent intent = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(intent);
    }

    public void enlaceComida(View view){
        Uri uriUrl = Uri.parse("https://turismo.euskadi.eus/aa30-18492/es/s12PortalWar/buscadoresJSP/buscadorJ1.jsp?r01kLang=es");
        Intent intent = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(intent);
    }
}
