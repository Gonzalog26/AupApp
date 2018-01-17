package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import eus.ehu.tta.aupapp.modelo.Event;
import eus.ehu.tta.aupapp.modelo.User;
import eus.ehu.tta.aupapp.negocio.ProgressTask;
import eus.ehu.tta.aupapp.negocio.ServidorNegocio;

public class VerEventosActivity extends AppCompatActivity {


    ServidorNegocio servidorNegocio = ServidorNegocio.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_eventos);
    }

    public void verEventos(View view){

        //Funcion para crear la View con los eventos entre las fechas indicadas
        final String baseUrl = "http://u017633.ehu.eus:28080/static/AupaAppRest/";

        final int fechaInicial = Integer.parseInt(((EditText)findViewById(R.id.fecha_inicial)).getText().toString());
        final int fechaFinal = Integer.parseInt(((EditText)findViewById(R.id.fecha_final)).getText().toString());

        new ProgressTask<List<Event>>(this){

            @Override
            protected List<Event> work() throws IOException,JSONException {
                return servidorNegocio.getEventos(fechaInicial,fechaFinal,"ggi2");
            }

            @Override
            protected void onFinish(List<Event> result){

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout);
                linearLayout.removeAllViews();

                for(int i=0;i<result.size();i++){

                    /*ImageView imageView = new ImageView(context);
                    Drawable drawable = Drawable.createFromPath(baseUrl+result.get(i).getFoto());
                    imageView.setImageDrawable(drawable);
                    linearLayout.addView(imageView);*/

                    TextView textView = new TextView(context);
                    textView.setText("Nombre: "+result.get(i).getNombre());
                    linearLayout.addView(textView);

                    TextView textView2 = new TextView(context);
                    textView.setText("Fecha: "+result.get(i).getFecha());
                    linearLayout.addView(textView2);

                    TextView textView3 = new TextView(context);
                    textView.setText("Hora: "+result.get(i).getHora());
                    linearLayout.addView(textView3);

                    TextView textView4 = new TextView(context);
                    textView.setText("Descripcion: "+result.get(i).getDescripcion());
                    linearLayout.addView(textView4);


                }




            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Toast.makeText(getApplicationContext(), R.string.errorvereventos, Toast.LENGTH_SHORT).show();
            }

        }.execute();

    }
}
