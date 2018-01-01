package eus.ehu.tta.aupapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ConoceBizkaiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conoce_bizkaia);
    }

    public void lugares(View view){
        Intent intent = new Intent(this, LugaresBizkaiaActivity.class);
        startActivity(intent);
    }

    public void cultura(View view){
        Intent intent = new Intent(this, CulturaBizkaiaActivity.class);
        startActivity(intent);
    }
}
