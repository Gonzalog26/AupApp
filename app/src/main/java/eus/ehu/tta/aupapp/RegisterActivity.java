package eus.ehu.tta.aupapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

import eus.ehu.tta.aupapp.modelo.User;
import eus.ehu.tta.aupapp.negocio.ProgressTask;
import eus.ehu.tta.aupapp.negocio.ServidorNegocio;


public class RegisterActivity extends AppCompatActivity {


    ServidorNegocio servidorNegocio = new ServidorNegocio();

    private final int PICTURE_REQUEST_CODE = 1;

    private final int WRITE_PERMISSION_CODE = 2;

    Uri pictureUri;
    public static String Provisional_Path;
    public static String Final_Path="/storage/emulated/0/Pictures/";

    File file;

    String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registro(View view){

        nombre = ((EditText)findViewById(R.id.nombre)).getText().toString();
        final String papellido = ((EditText)findViewById(R.id.papellido)).getText().toString();
        final String sapellido = ((EditText)findViewById(R.id.sapellido)).getText().toString();
        final String password = ((EditText)findViewById(R.id.password)).getText().toString();


        new ProgressTask<String>(this){

            @Override
            protected String work() throws IOException, JSONException {
                return servidorNegocio.registro(nombre, papellido, sapellido,password);
            }

            @Override
            protected void onFinish(String result){


                File file = new File(pictureUri.getPath());
                Final_Path = Final_Path+result+".jpg";
                File file2 = new File(Final_Path);


                file.renameTo(file2);

                Intent intent = new Intent(context, MenuActivity.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_LOGIN",result);
                //extras.putString("EXTRA_NOMBRE",nombre);
                //extras.putString("EXTRA_PAPELLIDO",papellido);
                extras.putString("EXTRA_SAPELLIDO",sapellido);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nombre",nombre);
                editor.putString("PrimerApellido",papellido);
                editor.commit();

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                intent.putExtras(extras);
                startActivity(intent);
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
                    Provisional_Path=pictureUri.getPath();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {

            case PICTURE_REQUEST_CODE:

                ImageView imageView = (ImageView) findViewById(R.id.foto_perfil_registro);
                Picasso.with(this).load("file://"+Provisional_Path).resize(1000,1000).into(imageView);
                imageView.setVisibility(View.VISIBLE);

                break;
        }
    }




}
