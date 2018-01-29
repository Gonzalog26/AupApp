package eus.ehu.tta.aupapp.Presentador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import eus.ehu.tta.aupapp.R;

public class DiaDiaActivity extends AppCompatActivity {

    int numeroPag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_dia);
    }

    @Override
    public void onBackPressed(){

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

    public void volverAlMenu(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_LOGIN","");
        extras.putString("EXTRA_NOMBRE","");
        extras.putString("EXTRA_PAPELLIDO","");
        extras.putString("EXTRA_SAPELLIDO","");
        extras.putString("Actividad","diadia");
        intent.putExtras(extras);
        startActivity(intent);
    }
}
