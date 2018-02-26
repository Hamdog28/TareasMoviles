package com.example.mariapizarro.tarea1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Clear(View view){
        TextView resultado = findViewById(R.id.resultado);
        resultado.setText("");
    }
    public void Convertir(View view){
        EditText editText = findViewById(R.id.editText);
        String monto = editText.getText().toString();

        TextView resultado = findViewById(R.id.resultado);
        String res = "";

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton colones = findViewById(R.id.radioColones);

        CharSequence text = "Ingrese un monto";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, text, duration);


        if (! monto.matches("")) {

            switch (radioGroup.getCheckedRadioButtonId()) {
                //dolares a colones
                case R.id.radioColones:
                    res = "₡" + (Double.parseDouble(0+monto)*567.72);
                    break;
                //colones a dolares
                case R.id.radioDolares:
                    res = "$" + (Double.parseDouble(0+monto)/567.72);
                    break;
            }
            resultado.setText(res);
        }
        else {
            toast.show();
            resultado.setText(res);
        }

    }
}
