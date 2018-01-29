package eus.ehu.tta.aupapp.Presentador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import eus.ehu.tta.aupapp.R;
import eus.ehu.tta.aupapp.modelo.User;
import eus.ehu.tta.aupapp.modelo.ProgressTask;
import eus.ehu.tta.aupapp.modelo.ServidorNegocio;

public class LoginActivity extends AppCompatActivity {


    ServidorNegocio servidorNegocio = ServidorNegocio.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void iniciarSesion(View view){

        final String login = ((EditText)findViewById(R.id.login)).getText().toString();
        final String passwd = ((EditText)findViewById(R.id.password)).getText().toString();

        new ProgressTask<User>(this){

            @Override
            protected User work() throws IOException,JSONException{
                return servidorNegocio.autenticathion(login,passwd);
            }

            @Override
            protected void onFinish(User result){

                if(result.getNombre()!=null){
                    Intent intent = new Intent(context,MenuActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("EXTRA_LOGIN",login);
                    extras.putString("EXTRA_NOMBRE",result.getNombre());
                    extras.putString("EXTRA_PAPELLIDO",result.getPapellido());
                    extras.putString("EXTRA_SAPELLIDO",result.getSapellido());
                    extras.putString("Actividad","login");
                    intent.putExtras(extras);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.nocoincidencias, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Toast.makeText(getApplicationContext(), R.string.errorlogeo, Toast.LENGTH_SHORT).show();
            }

        }.execute();

    }
}
