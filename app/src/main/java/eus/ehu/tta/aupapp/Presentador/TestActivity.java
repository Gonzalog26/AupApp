package eus.ehu.tta.aupapp.Presentador;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

import eus.ehu.tta.aupapp.R;
import eus.ehu.tta.aupapp.modelo.Test;
import eus.ehu.tta.aupapp.modelo.ServidorNegocio;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{


    public static String  NUMERO_PAG;

    int respuestasCorrecta;
    String urlVideo;
    int numeroPag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        int respondidas = sharedPreferences.getInt("respondidas",0);
        int correctas = sharedPreferences.getInt("correctas",0);

        TextView textView = (TextView)findViewById(R.id.resultados_valor);
        textView.setText(correctas+"/"+respondidas);

        if(correctas==0 || respondidas==0){
            textView= (TextView)findViewById(R.id.resultados_porcentaje);
            textView.setText("");
        }
        else{
            float porcentaje = (float)correctas/respondidas;
            textView= (TextView)findViewById(R.id.resultados_porcentaje);
            textView.setText(porcentaje*100+"%");
        }


        Intent intent = getIntent();
        numeroPag = intent.getIntExtra(NUMERO_PAG, 0);


        if(numeroPag==0 || numeroPag == 4 || numeroPag == 5){
            Button bt = findViewById(R.id.boton_next);
            bt.setText(R.string.vovlerMenuTests);
        }

        ServidorNegocio generadorTest = new ServidorNegocio();
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

        if(numeroPag==4 || numeroPag==1 || numeroPag == 5 || numeroPag == 6){

            Intent intent = new Intent(this,DiaDiaActivity.class);
            startActivity(intent);

        }else{

            if(numeroPag==3){
               Button bt = findViewById(R.id.boton_next);
               bt.setText(R.string.vovlerMenuTests);
            }

            findViewById(R.id.video_ayuda).setVisibility(View.VISIBLE);
            findViewById(R.id.video).setVisibility(View.GONE);
            findViewById(R.id.boton_next).setVisibility(View.GONE);

            RadioGroup group = (RadioGroup) findViewById(R.id.elecciones_test);
            group.removeAllViews();

            ServidorNegocio generadorTest = new ServidorNegocio();
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


    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, DiaDiaActivity.class);
        startActivity(intent);
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
                    ((Activity)getContext()).finish();
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

            SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
            int respondidas = sharedPreferences.getInt("respondidas",0)+1;
            int correctas = sharedPreferences.getInt("correctas",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("respondidas",respondidas);
            editor.commit();

            float porcentaje = (float)correctas/respondidas;

            TextView textView = (TextView)findViewById(R.id.resultados_valor);
            textView.setText(correctas+"/"+respondidas);

            textView= (TextView)findViewById(R.id.resultados_porcentaje);
            textView.setText(porcentaje*100+"%");

        }
        else{
            Toast.makeText(getApplicationContext(), "¡Correcto!", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
            int respondidas = sharedPreferences.getInt("respondidas",0)+1;
            int correctas = sharedPreferences.getInt("correctas",0)+1;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("respondidas",respondidas);
            editor.putInt("correctas",correctas);
            editor.commit();

            float porcentaje = (float)correctas/respondidas;

            TextView textView = (TextView)findViewById(R.id.resultados_valor);
            textView.setText(correctas+"/"+respondidas);

            textView= (TextView)findViewById(R.id.resultados_porcentaje);
            textView.setText(porcentaje*100+"%");
        }
    }

    public void onClick(View view){
        findViewById(R.id.boton_corregir).setVisibility(View.VISIBLE);
    }
}
