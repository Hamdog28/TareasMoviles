package com.example.mariapizarro.reproductormp3;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private StringBuilder text = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("bohemian_letra.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                text.append(mLine);
                text.append('\n');
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"Error reading file!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

            TextView tv = findViewById(R.id.letra);



        Animation translatebu= AnimationUtils.loadAnimation(this, R.anim.animationfile);
        tv.setText((CharSequence) text);
        tv.startAnimation(translatebu);

        Button button = (Button)findViewById( R.id.button );
        button.setTypeface(font);

        Button button2 = (Button)findViewById( R.id.button2 );
        button2.setTypeface(font);

        Button button3 = (Button)findViewById( R.id.button3 );
        button3.setTypeface(font);
    }
}
