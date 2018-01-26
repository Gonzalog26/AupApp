package eus.ehu.tta.aupapp;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import eus.ehu.tta.aupapp.negocio.ProgressTask;
import eus.ehu.tta.aupapp.negocio.ServidorNegocio;

public class CrearEventoActivity extends AppCompatActivity {

    ServidorNegocio servidorNegocio = ServidorNegocio.getInstancia();

    private final int PICTURE_REQUEST_CODE = 1;
    private final int WRITE_PERMISSION_CODE = 2;

    EditText hora;
    int hora2;

    Uri pictureUri;
    public static String PATH;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);
    }

    public void crearEvento(View view) throws IOException, JSONException {

        //Funciones necesarias para crear el evento

        final String nombre = ((EditText)findViewById(R.id.nombre_evento)).getText().toString();
        final String descripcion = ((EditText)findViewById(R.id.descripcion_evento)).getText().toString();
        final String ubicacion = ((EditText)findViewById(R.id.ubicacion_evento)).getText().toString();

        final int hora =  Integer.parseInt(((EditText)findViewById(R.id.hora_evento)).getText().toString());
        final int fecha = Integer.parseInt(((EditText)findViewById(R.id.fecha_evento)).getText().toString());


        new ProgressTask<Integer>(this){

            @Override
            protected Integer work() throws IOException, JSONException {
                String[] strings = PATH.split("/");
                String fileName = strings[strings.length - 1];
                InputStream is = null;
                File file = new File(PATH);
                is = new FileInputStream(file);

                return servidorNegocio.addEventos(nombre,descripcion, fecha, hora, fileName, ubicacion,"ggi2",is);
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

                ImageView imageView = (ImageView) findViewById(R.id.foto_perfil2);
                Picasso.with(this).load("file://"+PATH).resize(1000,1000).into(imageView);
                imageView.setVisibility(View.VISIBLE);

                break;
        }
    }


}
