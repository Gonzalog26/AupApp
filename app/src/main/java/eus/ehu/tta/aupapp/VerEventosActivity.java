package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
        final String baseUrl = "http://u017633.ehu.eus:28080/static/AupaAppRest/img/";

        /*final int fechaInicial = Integer.parseInt(((EditText)findViewById(R.id.fecha_inicial)).getText().toString());
        final int fechaFinal = Integer.parseInt(((EditText)findViewById(R.id.fecha_final)).getText().toString());*/

        final int fechaInicial = 20171220;
        final int fechaFinal = 20171221;

        new ProgressTask<List<Event>>(this){

            @Override
            protected List<Event> work() throws IOException,JSONException {
                return servidorNegocio.getEventos(fechaInicial,fechaFinal,"ggi2");
            }

            @Override
            protected void onFinish(List<Event> result){

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout);

                Button button = (Button)findViewById(R.id.boton_ubicacion);

                linearLayout.removeAllViews();

                for(int i=0;i<result.size();i++){

                    TextView textView = new TextView(context);
                    textView.setText(result.get(i).getNombre());
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0,0,0,50);
                    layoutParams.gravity=Gravity.CENTER;
                    textView.setLayoutParams(layoutParams);
                    textView.setTypeface(null, Typeface.BOLD);
                    linearLayout.addView(textView);

                    ImageView imageView = new ImageView(context);
                    Picasso.with(context).load(baseUrl+result.get(i).getFoto()).resize(1000,1000).into(imageView);
                    linearLayout.addView(imageView);

                    textView = new TextView(context);
                    textView.setText("Fecha: "+result.get(i).getFecha());
                    linearLayout.addView(textView);

                    textView = new TextView(context);
                    textView.setText("Hora: "+result.get(i).getHora());
                    linearLayout.addView(textView);

                    textView = new TextView(context);
                    textView.setText("Descripcion: "+result.get(i).getDescripcion());
                    linearLayout.addView(textView);

                    /*button.setVisibility(View.VISIBLE);
                    linearLayout.addView(button);*/

                    /*layoutParams.gravity=Gravity.NO_GRAVITY;
                    textView.setLayoutParams(layoutParams);*/


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
