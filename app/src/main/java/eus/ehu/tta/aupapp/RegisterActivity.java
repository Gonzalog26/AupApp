package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import eus.ehu.tta.aupapp.modelo.User;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registro(View view){

        String nombre = ((EditText)findViewById(R.id.nombre)).getText().toString();
        String papellido = ((EditText)findViewById(R.id.papellido)).getText().toString();
        String sapellido = ((EditText)findViewById(R.id.sapellido)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();

        String login = User.registro(nombre,papellido,sapellido,password);

        Intent intent = new Intent(this, MenuActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_NOMBRE",login);
        extras.putString("EXTRA_NOMBRE",nombre);
        extras.putString("EXTRA_PAPELLIDO",papellido);
        extras.putString("EXTRA_PAPELLIDO",sapellido);
        intent.putExtras(extras);
        startActivity(intent);

    }
}
