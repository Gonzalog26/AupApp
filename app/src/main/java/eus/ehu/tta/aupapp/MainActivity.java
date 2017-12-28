package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void castellano(View view){
        Locale localizacion = new Locale("es","Es");
        Locale.setDefault(localizacion);
        Configuration conf = new Configuration();
        conf.locale = localizacion;
        getBaseContext().getResources().updateConfiguration(conf,getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public void ingles(View view){
        Locale localizacion = new Locale("en","En");
        Locale.setDefault(localizacion);
        Configuration conf = new Configuration();
        conf.locale = localizacion;
        getBaseContext().getResources().updateConfiguration(conf,getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public void frances(View view){
        Locale localizacion = new Locale("fr","Fr");
        Locale.setDefault(localizacion);
        Configuration conf = new Configuration();
        conf.locale = localizacion;
        getBaseContext().getResources().updateConfiguration(conf,getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

}
