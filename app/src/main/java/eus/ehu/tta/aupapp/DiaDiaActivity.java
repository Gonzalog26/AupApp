package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DiaDiaActivity extends AppCompatActivity {

    int numeroPag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_dia);
    }

    public void irPreguntarDireccion(View view){
        Intent intent = new Intent(this, TestActivity.class);
        numeroPag=0;
        intent.putExtra(TestActivity.NUMERO_PAG,numeroPag);
        startActivity(intent);

    }

    public void irSaludosyDespedidas(View view){
        Intent intent = new Intent(this, TestActivity.class);
        numeroPag=1;
        intent.putExtra(TestActivity.NUMERO_PAG,numeroPag);
        startActivity(intent);
    }

    public void irIniciarConversacion(View view){
        Intent intent = new Intent(this, TestActivity.class);
        numeroPag=4;
        intent.putExtra(TestActivity.NUMERO_PAG,numeroPag);
        startActivity(intent);
    }

    public void irPrecio(View view){
        Intent intent = new Intent(this, TestActivity.class);
        numeroPag=5;
        intent.putExtra(TestActivity.NUMERO_PAG,numeroPag);
        startActivity(intent);
    }
}
