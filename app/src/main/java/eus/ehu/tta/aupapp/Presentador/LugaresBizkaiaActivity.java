package eus.ehu.tta.aupapp.Presentador;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import eus.ehu.tta.aupapp.R;

public class LugaresBizkaiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares_bizkaia);

    }

    public void enlaceMuseos(View view){
        Uri uriUrl = Uri.parse("https://turismovasco.com/bizkaia/que-ver-bizkaia/museos-de-bizkaia/");
        Intent intent = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(intent);
    }

    public void enlaceFiesta(View view){
        Uri uriUrl = Uri.parse("https://turismovasco.com/bizkaia/donde-salir-noche-bilbao/");
        Intent intent = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(intent);
    }

    public void enlacePlayas(View view){
        Uri uriUrl = Uri.parse("https://www.booking.com/destinationfinder/mountains/regions/es/vizcaya.es.html");
        Intent intent = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(intent);
    }

    public void enlaceCentrosComerciales(View view){
        Uri uriUrl = Uri.parse("http://www.minube.com/tag/centros-comerciales-vizcaya-z1035");
        Intent intent = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(intent);
    }
}
