package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CulturaBizkaiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultura_bizkaia);
    }

    public void enlaceBailes(View view){
        Uri uriUrl = Uri.parse("https://www.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(intent);
    }

    public void enlaceComida(View view){
        Uri uriUrl = Uri.parse("https://www.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(intent);
    }
}
