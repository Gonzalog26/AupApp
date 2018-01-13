package eus.ehu.tta.aupapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import eus.ehu.tta.aupapp.modelo.User;


public class RegisterActivity extends AppCompatActivity {


    private final int PICTURE_REQUEST_CODE = 1;

    private final int WRITE_PERMISSION_CODE = 2;

    Uri pictureUri;

    File file;

    String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registro(View view){

        nombre = ((EditText)findViewById(R.id.nombre)).getText().toString();
        String papellido = ((EditText)findViewById(R.id.papellido)).getText().toString();
        String sapellido = ((EditText)findViewById(R.id.sapellido)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();

        String login = User.registro(nombre,papellido,sapellido,password);

        Intent intent = new Intent(this, MenuActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_LOGIN",login);
        extras.putString("EXTRA_NOMBRE",nombre);
        extras.putString("EXTRA_PAPELLIDO",papellido);
        extras.putString("EXTRA_SAPELLIDO",sapellido);
        intent.putExtras(extras);
        startActivity(intent);

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
                    nombre = ((EditText)findViewById(R.id.nombre)).getText().toString();
                    file = File.createTempFile(nombre,".jpg",dir);
                    pictureUri = Uri.fromFile(file);
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

                //Toast.makeText(this, pictureUri.toString(), Toast.LENGTH_SHORT).show();
                //Bitmap bitmap = BitmapFactory.decodeFile(pictureUri.toString());

                Drawable drawable = Drawable.createFromPath(pictureUri.getPath());
                ImageView imageView = findViewById(R.id.foto_perfil);
                imageView.setImageDrawable(drawable);
                imageView.setVisibility(View.VISIBLE);
                drawable.

                break;
        }
    }


}
