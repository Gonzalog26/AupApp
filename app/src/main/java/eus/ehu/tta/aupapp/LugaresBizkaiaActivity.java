package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LugaresBizkaiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares_bizkaia);

    }

    public void enlaceMuseos(View view){
        Uri uriUrl = Uri.parse("https://www.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(intent);
    }

    public void enlaceFiesta(View view){
        Uri uriUrl = Uri.parse("https://www.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(intent);
    }

    public void enlacePlayas(View view){
        Uri uriUrl = Uri.parse("https://www.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(intent);
    }

    public void enlaceCentrosComerciales(View view){
        Uri uriUrl = Uri.parse("https://www.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(intent);
    }
}
