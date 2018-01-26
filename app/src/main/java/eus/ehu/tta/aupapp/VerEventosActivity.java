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
import android.widget.GridLayout;
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

public class VerEventosActivity extends AppCompatActivity implements View.OnClickListener{


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

        final int fechaInicial = 20171200;
        final int fechaFinal = 20180100;

        new ProgressTask<List<Event>>(this){

            @Override
            protected List<Event> work() throws IOException,JSONException {
                return servidorNegocio.getEventos(fechaInicial,fechaFinal,"ggi2");
            }

            @Override
            protected void onFinish(List<Event> result){

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_principal);

                linearLayout.removeAllViews();
                linearLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);


                for(int i=0;i<result.size();i++){

                    TextView textView = new TextView(context);
                    textView.setText(result.get(i).getNombre());
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    layoutParams1.setMargins(50,100,50,100);
                    layoutParams1.gravity=Gravity.CENTER_HORIZONTAL; //NO lo hace
                    textView.setLayoutParams(layoutParams1);
                    textView.setTypeface(null, Typeface.BOLD);
                    linearLayout.addView(textView);

                    ImageView imageView = new ImageView(context);
                    Picasso.with(context).load(baseUrl+result.get(i).getFoto()).resize(400,400).into(imageView);
                    linearLayout.addView(imageView);

                    textView = new TextView(context);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    layoutParams2.setMargins(50,100,50,0);
                    textView.setLayoutParams(layoutParams2);
                    Integer fecha = result.get(i).getFecha();
                    String fecha2 = fecha.toString().substring(6,8)+"/"+fecha.toString().substring(4,6)+"/"+fecha.toString().substring(0,4);
                    textView.setText("Fecha: "+fecha2);
                    linearLayout.addView(textView);

                    textView = new TextView(context);
                    LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    layoutParams3.setMargins(50,0,50,0);
                    textView.setLayoutParams(layoutParams3);
                    Integer hora = result.get(i).getHora();
                    String hora2;
                    if(hora<1000){
                        hora2 = hora.toString().substring(0,1)+":"+hora.toString().substring(1,3);
                    }else{
                        hora2 = hora.toString().substring(0,2)+":"+hora.toString().substring(2,4);
                    }

                    textView.setText("Hora: "+hora2);
                    linearLayout.addView(textView);

                    textView = new TextView(context);
                    textView.setLayoutParams(layoutParams3);
                    textView.setText("Descripcion: "+result.get(i).getDescripcion());
                    linearLayout.addView(textView);

                    Button button = new Button(context);
                    button.setId(i);
                    LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    button.setLayoutParams(layoutParams4);
                    layoutParams4.setMargins(350,30,0,0);
                    button.setText(R.string.ver_ubicacion);
                    linearLayout.addView(button);



                }


            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Toast.makeText(getApplicationContext(), R.string.errorvereventos, Toast.LENGTH_SHORT).show();
            }

        }.execute();

    }

    public void onClick(View view){



    }

}


