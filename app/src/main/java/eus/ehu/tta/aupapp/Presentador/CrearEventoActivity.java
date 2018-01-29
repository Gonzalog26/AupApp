package eus.ehu.tta.aupapp.Presentador;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import eus.ehu.tta.aupapp.R;
import eus.ehu.tta.aupapp.modelo.ProgressTask;
import eus.ehu.tta.aupapp.modelo.ServidorNegocio;

public class CrearEventoActivity extends AppCompatActivity implements View.OnClickListener{

    boolean foto;
    //Variables necesarias para obtener la fecha
    int fecha;
    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Widgets
    EditText etFecha;
    ImageButton ibObtenerFecha;

    //Variables necesarias para obtener la hora
    int hora2;
    private static final String DOS_PUNTOS = ":";

    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    //Widgets
    EditText etHora;
    ImageButton ibObtenerHora;


    ServidorNegocio servidorNegocio = ServidorNegocio.getInstancia();

    private final int PICTURE_REQUEST_CODE = 1;
    private final int WRITE_PERMISSION_CODE = 2;

    Uri pictureUri;
    public static String PATH;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        foto=false;
        hora2=0;
        fecha=0;

        //Widget EditText donde se mostrara la fecha obtenida
        etFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);
        //Evento setOnClickListener - clic
        ibObtenerFecha.setOnClickListener(this);

        //Widget EditText donde se mostrara la hora obtenida
        etHora = (EditText) findViewById(R.id.et_mostrar_hora_picker);
        //Widget ImageButton del cual usaremos el evento clic para obtener la hora
        ibObtenerHora = (ImageButton) findViewById(R.id.ib_obtener_hora);
        //Evento setOnClickListener - clic
        ibObtenerHora.setOnClickListener(this);
    }

    public void crearEvento(View view) throws IOException, JSONException {

        final String nombre = ((EditText)findViewById(R.id.nombre_evento)).getText().toString();
        final String descripcion = ((EditText)findViewById(R.id.descripcion_evento)).getText().toString();
        final String ubicacion = ((EditText)findViewById(R.id.ubicacion_evento)).getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        final String login = sharedPreferences.getString("login","");

        if(nombre.compareToIgnoreCase("")==0 || descripcion.compareToIgnoreCase("")==0
                || ubicacion.compareToIgnoreCase("")==0 || foto==false
                || hora2==0 || fecha==0){
            Toast.makeText(this, R.string.nohuecos, Toast.LENGTH_SHORT).show();
        }
        else{

            new ProgressTask<Integer>(this){

                @Override
                protected Integer work() throws IOException, JSONException {
                    String[] strings = PATH.split("/");
                    String fileName = strings[strings.length - 1];

                    InputStream is = null;
                    File file = new File(PATH);
                    is = new FileInputStream(file);

                    return servidorNegocio.addEventos(nombre,descripcion, fecha, hora2, fileName, ubicacion,login,is);
                }

                @Override
                protected void onFinish(Integer result){

                    if(result==200){
                        Toast.makeText(getApplicationContext(), R.string.evento_correcto, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,EventosMenuActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), R.string.evento_no_correcto, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                protected void onCancelled() {
                    super.onCancelled();
                    Toast.makeText(getApplicationContext(), R.string.errorregistro, Toast.LENGTH_SHORT).show();
                }

            }.execute();

        }





    }

    public void sacarFoto(View view){

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            Toast.makeText(this, R.string.nocamara, Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    sacarFoto();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISSION_CODE);
                }
            } else
                Toast.makeText(this, R.string.noapp, Toast.LENGTH_SHORT).show();

        }


    }

    private void sacarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                try {
                    file = File.createTempFile("provisional",".jpg",dir);
                    pictureUri = Uri.fromFile(file);
                    PATH = pictureUri.getPath();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                    startActivityForResult(intent, PICTURE_REQUEST_CODE);
                } catch (IOException ex) {
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_CODE);
            }
        } else
            Toast.makeText(this, R.string.noapp, Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {

            case PICTURE_REQUEST_CODE:

                foto=true;
                ImageView imageView = (ImageView) findViewById(R.id.foto_perfil2);
                Picasso.with(this).load("file://"+PATH).resize(1000,1000).into(imageView);
                imageView.setVisibility(View.VISIBLE);

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;

            case R.id.ib_obtener_hora:
                obtenerHora();
                break;
        }
    }

    private void obtenerFecha(){
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
                etFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);

                fecha = anio*10000+Integer.parseInt(mesFormateado)*100+Integer.parseInt(diaFormateado);

            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */



        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                etHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);

                hora2=Integer.parseInt(horaFormateada)*100+Integer.parseInt(minutoFormateado);
            }

            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }


}
