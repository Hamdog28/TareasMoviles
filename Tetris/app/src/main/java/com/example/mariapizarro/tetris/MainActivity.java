package com.example.mariapizarro.tetris;

import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private static final int NUM_ROWS = 20;
    private static final int NUM_COLUMNS = 10;
    private String [ ] [ ] posiciones = new String [ 20 ] [ 10 ] ;
    private int num_fila = 3;
    private int num_columna = 5;
    private int pieza = 1;
    private int filas_eliminadas = 0;

    /*
    * 1 = linea -> celeste num fila 3
    * 2 = cuadro -> amarillo fila 1
    * 3 = L -> naranja fila 2
    * 4 = J -> azul fila 2
    * 5 = T -> rosado fila 1
    * 6 = S -> verde 1
    * 7 = Z -> rojo 1
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        Button button = (Button)findViewById( R.id.button_down );
        button.setTypeface(font);
        button = (Button)findViewById( R.id.button_right );
        button.setTypeface(font);
        button = (Button)findViewById( R.id.button_left );
        button.setTypeface(font);
        button = (Button)findViewById( R.id.button_rotate );
        button.setTypeface(font);


        llenarTabla();

        Random rand = new Random();
        pieza = rand.nextInt(7) +1;  //1 <= n <= 2

        if(pieza == 1)
            num_fila = 3;
        else if(pieza == 2 || pieza == 5 || pieza == 6 || pieza == 7)
            num_fila = 1;
        else if(pieza == 3 || pieza == 4)
            num_fila = 2;

        juego();

    }





    private void juego() {
        TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);

        CountDownTimer timer = new CountDownTimer(21000, 1090) {

            public void onTick(long millisUntilFinished) {

                if((int)millisUntilFinished/1000 <1 || num_fila < 0) {
                    this.cancel();

                    if (num_fila>0){
                        if(pieza == 1) {
                            for(int i = 2; i<6; i++){
                                posiciones[num_fila-i][num_columna-1]="c";
                            }
                        }
                        else if(pieza == 2){
                            for (int i = 0; i < 2; i++) {
                                posiciones[num_fila - i - 1][num_columna - 1] = "y";
                                posiciones[num_fila - i - 1][num_columna] = "y";
                            }
                        }
                        else if(pieza == 3){
                            for (int i = 1; i < 4; i++) {
                                posiciones[num_fila - i ][num_columna - 1] = "y";
                                if(i==1)
                                    posiciones[num_fila - i][num_columna] = "y";
                            }
                        }
                        else if(pieza == 4){
                            for (int i = 1; i < 4; i++) {
                                posiciones[num_fila - i ][num_columna] = "b";
                                if(i==1)
                                    posiciones[num_fila - i][num_columna - 1] = "b";
                            }
                        }
                        else if(pieza == 5){
                            for (int i = 0; i < 2; i++) {
                                posiciones[num_fila - i - 1][num_columna - 1] = "p";
                                if(i == 1) {
                                    posiciones[num_fila - i - 1][num_columna] = "p";
                                    posiciones[num_fila - i - 1][num_columna - 2] = "p";
                                }
                            }
                        }
                        else if(pieza == 6){

                            for (int i = 0; i < 2; i++) {
                                posiciones[num_fila - i - 1][num_columna + i - 1] = "g";
                                posiciones[num_fila - i - 1][num_columna + i] = "g";


                            }
                        }
                        else if(pieza == 7) {
                            for (int i = 0; i < 2; i++) {
                                posiciones[num_fila - i - 1][num_columna - i + 1] = "r";
                                posiciones[num_fila - i - 1][num_columna - i] = "r";


                            }
                        }

                    }
                    onFinish();
                }


                if((int)millisUntilFinished/1000 >19) {
                    click();
                }

                else {
                    borrar();
                }

                num_fila++;
                pintar();

                TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                for(int i=0;i<11;i++){
                    TableRow fila = (TableRow) tablero.getChildAt(0);
                    ImageView image = (ImageView) fila.getChildAt(i);
                    image.setImageResource(R.drawable.block_block);
                }




            }

            public void onFinish() {
                if(pieza == 6 && num_fila>0){

                    for (int i = 0; i < 2; i++) {
                        posiciones[num_fila - i - 1][num_columna + i - 1] = "g";
                        posiciones[num_fila - i - 1][num_columna + i] = "g";


                    }
                }

                Random rand = new Random();
                for (int i = 0; i < posiciones.length; i++) {
                    for (int j = 0; j < posiciones[i].length; j++) {
                        System.out.print(posiciones[i][j] + " ");
                    }
                    System.out.println();
                }
                pieza = rand.nextInt(7) +1;

                if(pieza == 1)
                    num_fila = 2;
                else if(pieza == 2 || pieza == 5 || pieza == 6 ||pieza == 7)
                    num_fila = 0;
                else if(pieza == 3 || pieza == 4)
                    num_fila = 1;

                num_columna = 5;

                revisarMatriz();
                if(num_fila!=-13)
                    juego();
                else{

                }


            }
            public void pintar() {
                try{
                    if (pieza == 1)
                        pintar_l();
                    else if (pieza == 2)
                        pintar_O();
                    else if (pieza == 3)
                        pintar_L();
                    else if (pieza == 4)
                        pintar_J();
                    else if (pieza == 5)
                        pintar_T();
                    else if (pieza == 6)
                        pintar_S();
                    else if (pieza == 7)
                        pintar_Z();
                }
                catch (ArrayIndexOutOfBoundsException e){
                    TextView fin = findViewById(R.id.Fin);
                    fin.setText("Fin del juego");
                    TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                    tablero.setVisibility(View.INVISIBLE);
                }
            }
            public void pintar_L(){
                TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                if(num_columna<10) {
                    if (num_fila > 2 && num_fila <= 20 && posiciones[num_fila-1][num_columna - 1] == null && posiciones[num_fila-1][num_columna] == null ) {
                        for (int i = 0; i < 3; i++) {
                            TableRow fila = (TableRow) tablero.getChildAt(num_fila - i);
                            ImageView image = (ImageView) fila.getChildAt(num_columna);
                            image.setImageResource(R.drawable.block_orange);
                            if(i == 0) {
                                image = (ImageView) fila.getChildAt(num_columna + 1);
                                image.setImageResource(R.drawable.block_orange);
                            }
                        }
                    } else if (num_fila > 2) {
                        for (int i = 1; i < 4; i++) {
                            ;
                            TableRow fila = (TableRow) tablero.getChildAt(num_fila - i);
                            ImageView image = (ImageView) fila.getChildAt(num_columna);
                            image.setImageResource(R.drawable.block_orange);

                            if(i == 1) {
                                image = (ImageView) fila.getChildAt(num_columna + 1);
                                image.setImageResource(R.drawable.block_orange);
                                posiciones[num_fila - i - 1][num_columna] = "o";
                            }

                            posiciones[num_fila - i - 1][num_columna - 1] = "o";

                        }
                        num_fila = -100;
                    }
                }
                else {
                    num_columna--;

                    pintar_L();
                }
            }
            public void pintar_J(){

                TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                if(num_columna<10) {
                    if (num_fila > 2 && num_fila <= 20 && posiciones[num_fila-1][num_columna - 1] == null && posiciones[num_fila-1][num_columna] == null ) {
                        for (int i = 0; i < 3; i++) {
                            TableRow fila = (TableRow) tablero.getChildAt(num_fila - i);
                            ImageView image = (ImageView) fila.getChildAt(num_columna+1);
                            image.setImageResource(R.drawable.block_blue);
                            if(i == 0) {
                                image = (ImageView) fila.getChildAt(num_columna);
                                image.setImageResource(R.drawable.block_blue);
                            }
                        }
                    } else if (num_fila > 2) {
                        for (int i = 1; i < 4; i++) {

                            TableRow fila = (TableRow) tablero.getChildAt(num_fila - i);
                            ImageView image = (ImageView) fila.getChildAt(num_columna+1);
                            image.setImageResource(R.drawable.block_blue);

                            if(i == 1) {
                                image = (ImageView) fila.getChildAt(num_columna);
                                image.setImageResource(R.drawable.block_blue);
                                posiciones[num_fila - i - 1][num_columna - 1] = "b";
                            }

                            posiciones[num_fila - i - 1][num_columna] = "b";

                        }
                        num_fila = -100;
                    }
                }
                else {
                    num_columna--;

                    pintar_J();
                }

            }
            public void pintar_T(){

                TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                if(num_columna<10) {
                    if (num_fila > 1 && num_fila <= 20 && posiciones[num_fila-1][num_columna - 1] == null && posiciones[num_fila-2][num_columna] == null && posiciones[num_fila-2][num_columna-2] == null) {
                        for (int i = 0; i < 2; i++) {
                            TableRow fila = (TableRow) tablero.getChildAt(num_fila - i);
                            ImageView image = (ImageView) fila.getChildAt(num_columna);
                            image.setImageResource(R.drawable.block_pink);

                            if(i == 1) {
                                image = (ImageView) fila.getChildAt(num_columna + 1);
                                image.setImageResource(R.drawable.block_pink);
                                image = (ImageView) fila.getChildAt(num_columna -1);
                                image.setImageResource(R.drawable.block_pink);
                            }
                        }


                    } else if (num_fila > 1) {
                        for (int i = 1; i < 3; i++) {

                            TableRow fila = (TableRow) tablero.getChildAt(num_fila - i);
                            ImageView image = (ImageView) fila.getChildAt(num_columna);
                            image.setImageResource(R.drawable.block_pink);

                            if(i == 2) {
                                image = (ImageView) fila.getChildAt(num_columna + 1);
                                image.setImageResource(R.drawable.block_pink);
                                image = (ImageView) fila.getChildAt(num_columna - 1);
                                image.setImageResource(R.drawable.block_pink);

                                posiciones[num_fila - i - 1][num_columna] = "p";
                                posiciones[num_fila - i - 1][num_columna - 2] = "p";
                            }

                            posiciones[num_fila - i - 1][num_columna - 1] = "p";

                        }
                        num_fila = -100;
                    }
                }
                else {
                    num_columna--;

                    pintar_T();
                }
            }
            public void pintar_Z(){

                TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                if(num_columna<10) {
                    if (num_fila > 1 && num_fila <= 20 && posiciones[num_fila-1][num_columna - 1] == null && posiciones[num_fila-1][num_columna] == null && posiciones[num_fila-2][num_columna-2] == null) {
                        for (int i = 0; i < 2; i++) {
                            TableRow fila = (TableRow) tablero.getChildAt(num_fila - i);
                            ImageView image = (ImageView) fila.getChildAt(num_columna - i + 1);
                            image.setImageResource(R.drawable.block_red);

                            image = (ImageView) fila.getChildAt(num_columna - i);
                            image.setImageResource(R.drawable.block_red);

                        }
                    } else if (num_fila > 1) {
                        for (int i = 1; i < 3; i++) {

                            TableRow fila = (TableRow) tablero.getChildAt(num_fila - i );
                            ImageView image = (ImageView) fila.getChildAt(num_columna - i + 2);
                            image.setImageResource(R.drawable.block_red);

                            image = (ImageView) fila.getChildAt(num_columna - i + 1);
                            image.setImageResource(R.drawable.block_red);


                                posiciones[num_fila - i - 1][num_columna  - i + 1] = "r";
                                posiciones[num_fila - i - 1][num_columna - i] = "r";


                        }
                        num_fila = -100;
                    }
                }
                else {
                    num_columna--;

                    pintar_Z();
                }

            }
            public void pintar_S(){

                TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                if(num_columna<10) {
                    if (num_fila > 1 && num_fila <= 20 && posiciones[num_fila-1][num_columna - 1] == null && posiciones[num_fila-2][num_columna] == null && posiciones[num_fila-1][num_columna-2] == null) {
                        for (int i = 0; i < 2; i++) {
                            TableRow fila = (TableRow) tablero.getChildAt(num_fila - i);
                            ImageView image = (ImageView) fila.getChildAt(num_columna + i);
                            image.setImageResource(R.drawable.block_green);

                            image = (ImageView) fila.getChildAt(num_columna + i-1);
                            image.setImageResource(R.drawable.block_green);

                        }
                    } else if (num_fila > 1) {
                        for (int i = 1; i < 3; i++) {

                            TableRow fila = (TableRow) tablero.getChildAt(num_fila - i );
                            ImageView image = (ImageView) fila.getChildAt(num_columna + i-1);
                            image.setImageResource(R.drawable.block_green);

                            image = (ImageView) fila.getChildAt(num_columna + i-2);
                            image.setImageResource(R.drawable.block_green);


                            posiciones[num_fila - i - 1][num_columna  + i - 2] = "g";
                            posiciones[num_fila - i - 1][num_columna + i - 3] = "g";


                        }
                        num_fila = -100;
                    }
                }
                else {
                    num_columna--;

                    pintar_S();
                }
            }
            public void pintar_O(){
                TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                if(num_columna<10) {
                    if (num_fila > 1 && num_fila <= 20 && posiciones[num_fila-1][num_columna - 1] == null && posiciones[num_fila-1][num_columna] == null ) {
                        for (int i = 0; i < 2; i++) {
                            TableRow fila = (TableRow) tablero.getChildAt(num_fila - i);
                            ImageView image = (ImageView) fila.getChildAt(num_columna);
                            image.setImageResource(R.drawable.block_yelow);

                            image = (ImageView) fila.getChildAt(num_columna + 1);
                            image.setImageResource(R.drawable.block_yelow);
                        }
                    } else if (num_fila > 1) {
                        for (int i = 1; i < 3; i++) {

                            TableRow fila = (TableRow) tablero.getChildAt(num_fila - i);
                            ImageView image = (ImageView) fila.getChildAt(num_columna);
                            image.setImageResource(R.drawable.block_yelow);

                            image = (ImageView) fila.getChildAt(num_columna + 1);
                            image.setImageResource(R.drawable.block_yelow);

                            posiciones[num_fila - i - 1][num_columna - 1] = "y";
                            posiciones[num_fila - i - 1][num_columna] = "y";
                        }
                        num_fila = -100;
                    }
                }
                else {
                    num_columna--;

                    pintar_O();
                }
            }

            public void pintar_l(){
                TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);

                if (num_fila > 3 && num_fila <= 20 && posiciones[num_fila - 1][num_columna - 1] == null) {
                    for(int i=0;i<4;i++){
                        TableRow fila = (TableRow) tablero.getChildAt(num_fila-i);
                        ImageView image = (ImageView) fila.getChildAt(num_columna);
                        image.setImageResource(R.drawable.block_lightblue);
                    }
                } else if(num_fila > 3){
                    for(int i = 1; i<5; i++){
                        TableRow fila = (TableRow) tablero.getChildAt(num_fila-i);
                        ImageView image = (ImageView) fila.getChildAt(num_columna);
                            image.setImageResource(R.drawable.block_lightblue);

                            posiciones[num_fila-i-1][num_columna-1]="c";
                        }
                        num_fila=-100;
                    }
            }

            public void borrar() {
                if (pieza == 1)
                    borrarl();
                else if (pieza == 2)
                    borrar_O();
                else if (pieza == 3)
                    borrar_L();
                else if (pieza == 4)
                    borrar_J();
                else if (pieza == 5)
                    borrar_T();
                else if (pieza == 6)
                    borrar_S();
                else if (pieza == 7)
                    borrar_Z();

            }
            public void borrarl(){
                if (num_fila > 2 & num_fila <= 20){
                    TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                    for(int i=0;i<4;i++) {
                        TableRow fila = (TableRow) tablero.getChildAt(num_fila-i);
                        ImageView image = (ImageView) fila.getChildAt(num_columna);
                        image.setImageDrawable(null);
                    }

                }
            }
            public void borrar_O(){
                if (num_fila > 0 & num_fila <= 20){
                    TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                    for(int i=0;i<2;i++) {
                        TableRow fila = (TableRow) tablero.getChildAt(num_fila-i);
                        ImageView image = (ImageView) fila.getChildAt(num_columna);
                        image.setImageDrawable(null);

                        image = (ImageView) fila.getChildAt(num_columna+1);
                        image.setImageDrawable(null);

                    }

                }
            }
            public void borrar_L(){
                if (num_fila > 1 & num_fila <= 20){
                    TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                    for(int i=0;i<3;i++) {
                        TableRow fila = (TableRow) tablero.getChildAt(num_fila-i);
                        ImageView image = (ImageView) fila.getChildAt(num_columna);
                        image.setImageDrawable(null);

                        if(i==0) {
                            image = (ImageView) fila.getChildAt(num_columna + 1);
                            image.setImageDrawable(null);
                        }


                    }

                }
            }
            public void borrar_J(){
                if (num_fila > 1 & num_fila <= 20){
                    TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                    for(int i=0;i<3;i++) {
                        TableRow fila = (TableRow) tablero.getChildAt(num_fila-i);
                        ImageView image = (ImageView) fila.getChildAt(num_columna + 1);
                        image.setImageDrawable(null);

                        if(i==0) {
                            image = (ImageView) fila.getChildAt(num_columna);
                            image.setImageDrawable(null);
                        }


                    }

                }
            }

            public void borrar_T(){

                if (num_fila > 0 & num_fila <= 20){
                    TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                    for(int i=0;i<2;i++) {
                        TableRow fila = (TableRow) tablero.getChildAt(num_fila-i);
                        ImageView image = (ImageView) fila.getChildAt(num_columna);
                        image.setImageDrawable(null);
                        if(i == 1) {
                            image = (ImageView) fila.getChildAt(num_columna + 1);
                            image.setImageDrawable(null);
                            image = (ImageView) fila.getChildAt(num_columna - 1);
                            image.setImageDrawable(null);
                        }

                    }

                }

            }
            public void borrar_Z(){
                if (num_fila > 0 & num_fila <= 20){
                    TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                    for(int i=0;i<2;i++) {
                        TableRow fila = (TableRow) tablero.getChildAt(num_fila-i);
                        ImageView image = (ImageView) fila.getChildAt(num_columna - i + 1);
                        image.setImageDrawable(null);

                        image = (ImageView) fila.getChildAt(num_columna - i);
                        image.setImageDrawable(null);


                    }

                }

            }
            public void borrar_S(){
                if (num_fila > 0 & num_fila <= 20){
                    TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                    for(int i=0;i<2;i++) {
                        TableRow fila = (TableRow) tablero.getChildAt(num_fila - i);
                        ImageView image = (ImageView) fila.getChildAt(num_columna + i);
                        image.setImageDrawable(null);

                        image = (ImageView) fila.getChildAt(num_columna + i - 1);
                        image.setImageDrawable(null);


                    }

                }
            }

            private void click(){
                Button botonIzquierdo = (Button) findViewById(R.id.button_left);

                botonIzquierdo.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        try {
                            if(num_columna>1){

                                if(pieza == 1 && posiciones[num_fila - 1][num_columna -2]==null && posiciones[num_fila - 2][num_columna -2]==null && posiciones[num_fila - 3][num_columna -2]==null && posiciones[num_fila - 4][num_columna -2]==null) {
                                    borrar();
                                    num_columna--;
                                    pintar();
                                }
                                else if(pieza == 2 && posiciones[num_fila-1][num_columna-2] == null && posiciones[num_fila - 2][num_columna-2]==null){
                                    borrar();
                                    num_columna--;
                                    pintar();
                                }
                                else if(pieza == 3 && posiciones[num_fila - 1][num_columna -2]==null && posiciones[num_fila - 2][num_columna -2]==null && posiciones[num_fila - 3][num_columna -2]==null){
                                    borrar();
                                    num_columna--;
                                    pintar();
                                }
                                else if(pieza == 4 && posiciones[num_fila - 1][num_columna-2]==null && posiciones[num_fila - 2][num_columna - 1]==null && posiciones[num_fila - 3][num_columna - 1]==null){
                                    borrar();
                                    num_columna--;
                                    pintar();
                                }
                                else if((pieza == 5 || pieza == 7) && posiciones[num_fila-1][num_columna-2] == null && posiciones[num_fila - 2][num_columna-3]==null){
                                    borrar();
                                    num_columna--;
                                    pintar();
                                }
                                else if(pieza == 6 && posiciones[num_fila-1][num_columna-3] == null && posiciones[num_fila - 2][num_columna-2]==null && num_columna>2){
                                    borrar();
                                    num_columna--;
                                    pintar();
                                }
                            }
                        }
                        catch(ArrayIndexOutOfBoundsException e){}
                    }});
                Button botonDerecho = (Button) findViewById(R.id.button_right);

                botonDerecho.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        try {
                            if (num_columna < 10 ) {

                                if (pieza == 1 && posiciones[num_fila - 1][num_columna] == null && posiciones[num_fila - 2][num_columna]==null && posiciones[num_fila - 3][num_columna]==null && posiciones[num_fila - 4][num_columna]==null) {
                                    borrar();
                                    num_columna++;
                                    pintar();
                                }
                                else if(pieza == 2 && posiciones[num_fila-1][num_columna+1] == null && posiciones[num_fila - 2][num_columna+1]==null){
                                    borrar();
                                    num_columna++;
                                    pintar();
                                }
                                else if(pieza == 3 && posiciones[num_fila - 2][num_columna]==null && posiciones[num_fila - 3][num_columna]==null && posiciones[num_fila-1][num_columna+1] == null){
                                    borrar();
                                    num_columna++;
                                    pintar();
                                }
                                else if(pieza == 4 && posiciones[num_fila - 2][num_columna+1]==null && posiciones[num_fila - 3][num_columna+1]==null && posiciones[num_fila-1][num_columna+1] == null){
                                    borrar();
                                    num_columna++;
                                    pintar();
                                }
                                else if( (pieza == 5 || pieza == 6) && posiciones[num_fila-1][num_columna] == null && posiciones[num_fila - 2][num_columna+1]==null){
                                    borrar();
                                    num_columna++;
                                    pintar();
                                }
                                else if(pieza == 7 && posiciones[num_fila-1][num_columna+1] == null && posiciones[num_fila - 2][num_columna]==null){

                                    borrar();
                                    num_columna++;
                                    pintar();
                                }
                            }
                        }
                        catch(ArrayIndexOutOfBoundsException e){
                        }
                    }});
                Button botonAbajo = (Button) findViewById(R.id.button_down);

                botonAbajo.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        if (num_fila < 20 ) {
                            borrar();
                            num_fila++;
                            pintar();
                        }
                    }});
            }
        };


        timer.start();

    }
    private void revisarMatriz(){
        for(int i = 20; i > 0 ; i--) {
            System.out.println("fila: " + Integer.toString(i));
            if (!Arrays.asList(posiciones[i-1]).contains(null)) {
                TextView puntaje = findViewById(R.id.puntaje);
                filas_eliminadas++;
                puntaje.setText(Integer.toString(filas_eliminadas));
                posiciones[i-1] = null;
                for (int j = i-1; j > 0; j--) {
                    posiciones[j] = posiciones[j - 1];

                    TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
                    for (int z = 1; z <= 10; z++) {
                        TableRow fila = (TableRow) tablero.getChildAt(j+1);
                        ImageView image = (ImageView) fila.getChildAt(z);

                        try {
                            String dato = posiciones[j][z - 1];

                            if (dato.equals("c")) {
                                image.setImageResource(R.drawable.block_lightblue);
                            } else if (dato.equals("y")) {
                                image.setImageResource(R.drawable.block_yelow);
                            } else if (dato.equals("o")) {
                                image.setImageResource(R.drawable.block_orange);
                            } else if (dato.equals("b")) {
                                image.setImageResource(R.drawable.block_blue);
                            } else if (dato.equals("p")) {
                                image.setImageResource(R.drawable.block_lightblue);
                            } else if (dato.equals("g")) {
                                image.setImageResource(R.drawable.block_green);
                            } else if (dato.equals("r")) {
                                image.setImageResource(R.drawable.block_red);
                            }
                        }catch(Exception e){
                            image.setImageDrawable(null);
                        }
                    }
                }


                /*
                for (int k = 0; i < posiciones.length; k++) {
                    for (int j = 0; j < posiciones[k].length; j++) {
                        System.out.print(posiciones[k][j] + " ");
                    }
                    System.out.println();
                }
                */
                i++;
            }

        }
        System.out.println("eliminadas " + Integer.toString(filas_eliminadas));
    }

    private void llenarTabla() {
        TableLayout tablero = (TableLayout) findViewById(R.id.Tablero);
        for(int row = 0; row < NUM_ROWS + 2; row++ ){
            TableRow tableRow = new TableRow(this);
            tablero.addView(tableRow);

                for (int column = 0; column < NUM_COLUMNS + 2; column++) {
                    ImageView image = new ImageView(this);

                    if (column == 0 || (column + 1 ) == NUM_COLUMNS + 2|| row == 0 || (row + 1) == NUM_ROWS + 2) {
                        image.setImageResource(R.drawable.block_block);
                    }
                    image.setAdjustViewBounds(true);
                    image.setTag("i" + Integer.toString(row) + Integer.toString(column));
                    //else
                    //image.setImageResource(R.drawable.block_blue);
                    tableRow.addView(image);

                }

        }
    }
}


