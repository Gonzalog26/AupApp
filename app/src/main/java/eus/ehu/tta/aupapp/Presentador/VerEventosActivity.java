package eus.ehu.tta.aupapp.Presentador;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import eus.ehu.tta.aupapp.R;
import eus.ehu.tta.aupapp.modelo.Event;
import eus.ehu.tta.aupapp.modelo.ProgressTask;
import eus.ehu.tta.aupapp.modelo.ServidorNegocio;

public class VerEventosActivity extends AppCompatActivity implements View.OnClickListener{

    //Variables necesarias para obtener la fecha
    int fecha_inicial;
    int fecha_final;
    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Widgets
    EditText etFecha_inicial;
    ImageButton ibObtenerFecha_inicial;
    EditText etFecha_final;
    ImageButton ibObtenerFecha_final;

    ServidorNegocio servidorNegocio = ServidorNegocio.getInstancia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_eventos);

        fecha_inicial=0;
        fecha_final=0;

        //Widget EditText donde se mostrara la fecha obtenida
        etFecha_inicial = (EditText) findViewById(R.id.et_mostrar_fecha_picker_inicial);
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha_inicial = (ImageButton) findViewById(R.id.ib_obtener_fecha_inicial);
        //Evento setOnClickListener - clic
        ibObtenerFecha_inicial.setOnClickListener(this);

        //Widget EditText donde se mostrara la fecha obtenida
        etFecha_final = (EditText) findViewById(R.id.et_mostrar_fecha_picker_final);
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha_final = (ImageButton) findViewById(R.id.ib_obtener_fecha_final);
        //Evento setOnClickListener - clic
        ibObtenerFecha_final.setOnClickListener(this);
    }

    public void verEventos(View view){

        //Funcion para crear la View con los eventos entre las fechas indicadas
        final String baseUrl = "http://u017633.ehu.eus:28080/static/AupaAppRest/img/";

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        final String login = sharedPreferences.getString("login","");

        if(fecha_inicial==0 || fecha_final==0){
            Toast.makeText(this,R.string.nohuecos,Toast.LENGTH_SHORT).show();
        }
        else{

            new ProgressTask<List<Event>>(this){

                @Override
                protected List<Event> work() throws IOException,JSONException {
                    return servidorNegocio.getEventos(fecha_inicial,fecha_final,login);
                }

                @Override
                protected void onFinish(List<Event> result){

                    if(result.size()==0){
                        Toast.makeText(context,R.string.nohayeventos,Toast.LENGTH_SHORT).show();
                    }
                    else{

                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_principal);

                        linearLayout.removeAllViews();

                        for(int i=0;i<result.size();i++){


                            TextView textView = new TextView(context);
                            textView.setText(result.get(i).getNombre());
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
                            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams1.setMargins(50,0,50,100);
                            textView.setLayoutParams(layoutParams1);
                            textView.setTypeface(null, Typeface.BOLD);
                            linearLayout.addView(textView);

                            ImageView imageView = new ImageView(context);
                            Picasso.with(context).load(baseUrl+result.get(i).getFoto()).resize(400,400).into(imageView);
                            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams3.setMargins(50,100,50,0);
                            imageView.setLayoutParams(layoutParams3);
                            linearLayout.addView(imageView);

                            GridLayout gridLayout = new GridLayout(context);
                            gridLayout.setColumnCount(2);
                            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            layoutParams2.setMargins(50,100,50,0);
                            textView.setLayoutParams(layoutParams2);
                            gridLayout.setLayoutParams(layoutParams2);

                            textView = new TextView(context);
                            textView.setText(R.string.fecha);
                            gridLayout.addView(textView);

                            textView = new TextView(context);
                            Integer fecha = result.get(i).getFecha();
                            String fecha2 = fecha.toString().substring(6,8)+"/"+fecha.toString().substring(4,6)+"/"+fecha.toString().substring(0,4);
                            textView.setText(fecha2);
                            gridLayout.addView(textView);

                            textView = new TextView(context);
                            textView.setText(R.string.hora);
                            gridLayout.addView(textView);

                            textView = new TextView(context);
                            Integer hora = result.get(i).getHora();
                            String hora2;
                            if(hora<1000){
                                hora2 = hora.toString().substring(0,1)+":"+hora.toString().substring(1,3);
                            }else{
                                hora2 = hora.toString().substring(0,2)+":"+hora.toString().substring(2,4);
                            }

                            textView.setText(hora2);
                            gridLayout.addView(textView);

                            textView = new TextView(context);
                            textView.setText(R.string.descripcion);
                            gridLayout.addView(textView);

                            textView = new TextView(context);
                            textView.setText(result.get(i).getDescripcion());
                            gridLayout.addView(textView);

                            linearLayout.addView(gridLayout);

                            Button button = new Button(context);
                            final String ubicacion = result.get(i).getUbicacion();
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    String address="";

                                    for (int i = 0; i < ubicacion.length(); i ++){
                                        address += (ubicacion.charAt(i) == ' ') ? "+" : ubicacion.charAt(i);
                                    }

                                    Uri gmmIntentUri = Uri.parse("geo:0,0?q="+address);
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    startActivity(mapIntent);

                                }
                            });
                            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams4.setMargins(350,30,0,0);
                            button.setLayoutParams(layoutParams4);
                            button.setText(R.string.ver_ubicacion);
                            linearLayout.addView(button);
                        }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha_inicial:
                obtenerFecha(0);
                break;

            case R.id.ib_obtener_fecha_final:
                obtenerFecha(1);
                break;

        }
    }

    private void obtenerFecha(final int i){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado

                if(i==0){
                    etFecha_inicial.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);

                    fecha_inicial = year*10000+Integer.parseInt(mesFormateado)*100+Integer.parseInt(diaFormateado);
                }
                else{
                    etFecha_final.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);

                    fecha_final = year*10000+Integer.parseInt(mesFormateado)*100+Integer.parseInt(diaFormateado);
                }


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */



        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }


}


