package eus.ehu.tta.aupapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import eus.ehu.tta.aupapp.modelo.Test;
import eus.ehu.tta.aupapp.negocio.GeneradorTest;

public class SaludosDespedidas2Activity extends AppCompatActivity implements View.OnClickListener{

    int respuestasCorrecta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        GeneradorTest generadorTest = new GeneradorTest();
        List<Test> tests = generadorTest.getTests();

        TextView enunciado = (TextView)findViewById(R.id.enunciado_test);
        enunciado.setText(tests.get(2).getEnunciado());

        respuestasCorrecta = tests.get(2).getRespuestCorrecta();

        RadioGroup group = (RadioGroup) findViewById(R.id.elecciones_test);
        int i=0;

        for( String resp : tests.get(2).getRespuestas()) {
            RadioButton radio = new RadioButton(this);
            radio.setId(i);
            radio.setText(resp);
            radio.setOnClickListener(this);
            group.addView(radio);
            i++;

        }
    }

    public void siguiente(View view){

    }

    public void corregirRespuesta(View view) {
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
