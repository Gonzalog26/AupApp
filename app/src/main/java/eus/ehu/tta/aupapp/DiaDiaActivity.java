package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DiaDiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_dia);
    }

    public void irPreguntarDireccion(View view){
        Intent intent = new Intent(this, PreguntarDireccionActivity.class);
        startActivity(intent);

    }

    public void irSaludosyDespedidas(View view){
        Intent intent = new Intent(this, SaludoosDespedidas1Activity.class);
        startActivity(intent);
    }
}
