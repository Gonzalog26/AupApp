package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import eus.ehu.tta.aupapp.modelo.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void iniciarSesion(View view){

        Intent intent = new Intent(this,MenuActivity.class);
        String login = ((EditText)findViewById(R.id.login)).getText().toString();
        String passwd = ((EditText)findViewById(R.id.password)).getText().toString();
        if(User.autenticathion(login, passwd)){
            intent.putExtra(MenuActivity.EXTRA_LOGIN,login);
            startActivity(intent);
        }
    }
}
