package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

import eus.ehu.tta.aupapp.modelo.Test;
import eus.ehu.tta.aupapp.negocio.GeneradorTest;

public class SaludoosDespedidas1Activity extends AppCompatActivity implements View.OnClickListener{


    int respuestasCorrecta;
    String urlVideo;
    int numeroPag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        numeroPag=1;

        GeneradorTest generadorTest = new GeneradorTest();
        List<Test> tests = generadorTest.getTests();

        TextView enunciado = (TextView)findViewById(R.id.enunciado_test);
        enunciado.setText(tests.get(numeroPag).getEnunciado());

        respuestasCorrecta = tests.get(numeroPag).getRespuestCorrecta();
        urlVideo = tests.get(numeroPag).getUrlVideo();

        RadioGroup group = (RadioGroup) findViewById(R.id.elecciones_test);
        int i=0;

        for( String resp : tests.get(numeroPag).getRespuestas()) {
            RadioButton radio = new RadioButton(this);
            radio.setId(i);
            radio.setText(resp);
            radio.setOnClickListener(this);
            group.addView(radio);
            i++;

        }
    }

    public void siguiente(View view){

        numeroPag++;

        findViewById(R.id.video_ayuda).setVisibility(View.VISIBLE);
        findViewById(R.id.video).setVisibility(View.GONE);
        findViewById(R.id.boton_next).setVisibility(View.GONE);

        RadioGroup group = (RadioGroup) findViewById(R.id.elecciones_test);
        group.removeAllViews();

        GeneradorTest generadorTest = new GeneradorTest();
        List<Test> tests = generadorTest.getTests();

        TextView enunciado = (TextView)findViewById(R.id.enunciado_test);
        enunciado.setText(tests.get(numeroPag).getEnunciado());

        respuestasCorrecta = tests.get(numeroPag).getRespuestCorrecta();
        urlVideo = tests.get(numeroPag).getUrlVideo();

        int i=0;

        for( String resp : tests.get(numeroPag).getRespuestas()) {
            RadioButton radio = new RadioButton(this);
            radio.setId(i);
            radio.setText(resp);
            radio.setOnClickListener(this);
            group.addView(radio);
            i++;

        }
    }

    public void verVideo(View view){
        findViewById(R.id.video_ayuda).setVisibility(View.GONE);
        findViewById(R.id.video).setVisibility(View.VISIBLE);

        VideoView videoView = findViewById(R.id.video);

        videoView.setVideoURI(Uri.parse(urlVideo));

        MediaController controller = new MediaController(this){

            @Override
            public void hide(){

            }

            public boolean dispathcKeyEvent(KeyEvent event){
                if(event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                    finish();
                return super.dispatchKeyEvent(event);
            }
        };

        controller.setAnchorView(videoView);
        videoView.setMediaController(controller);
        videoView.start();

    }

    public void corregirRespuesta(View view){
        RadioGroup radioGroup = findViewById(R.id.elecciones_test);
        int seleccionado = radioGroup.getCheckedRadioButtonId();

        findViewById(R.id.boton_corregir).setVisibility(View.GONE);
        findViewById(R.id.boton_next).setVisibility(View.VISIBLE);

        radioGroup.getChildAt(respuestasCorrecta).setBackgroundColor(Color.GREEN);

        if(seleccionado != respuestasCorrecta){
            radioGroup.getChildAt(seleccionado).setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(),"!Has fallado!",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "¡Correcto!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view){
        findViewById(R.id.boton_corregir).setVisibility(View.VISIBLE);
    }
}
