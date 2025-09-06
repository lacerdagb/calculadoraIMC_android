package com.example.calculadoraimc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Código inicial para configurar a tela cheia.
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });


        // Conecta as variáveis do código com os elementos da tela (layout).
        EditText editTextWeight = findViewById(R.id.edittext_weight);
        SeekBar seekBarHeight = findViewById(R.id.seekbar_height);
        Button buttonClear = findViewById(R.id.button_clear);
        Button buttonCalculate = findViewById(R.id.button_calculate);
        TextView textViewHeight = findViewById(R.id.textview_height);
        TextView textViewResult = findViewById(R.id.textview_result);

        // Define a ação que o botão "Calcular" fará ao ser clicado.
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tenta executar o cálculo, previnindo erros se os campos estiverem vazios.
                try {
                    // Pega o peso e a altura informados pelo usuário.
                    String weightText = editTextWeight.getText().toString();
                    double height = (double)seekBarHeight.getProgress() / 100;

                    // Valida se a altura é maior que zero para evitar erros.
                    if(height == 0) {
                        // Mostra uma mensagem de aviso para o usuário.
                        Toast.makeText(getApplicationContext(), R.string.msg_validation_height, Toast.LENGTH_LONG).show();

                    } else {
                        // Converte o peso para número e realiza o cálculo do IMC.
                        double weight = Double.parseDouble(weightText);
                        double result = weight / (height * height);

                        // Formata o resultado para ter apenas 2 casas decimais.
                        String value = String.format(Locale.getDefault(), "IMC: %.2f", result);
                        // Exibe o resultado na tela.
                        textViewResult.setText(value);
                        textViewResult.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    // Se o campo de peso estiver vazio, mostra um aviso.
                    Toast.makeText(getApplicationContext(), R.string.msg_validation_weight, Toast.LENGTH_LONG).show();
                }
            }
        });

        // Define o que acontece quando o usuário arrasta a barra de altura.
        seekBarHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Atualiza o texto da altura em tempo real.
                textViewHeight.setText(progress + "cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Não é necessário nenhuma ação aqui.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Não é necessário nenhuma ação aqui.
            }
        });

        // Define a ação do botão "Limpar" ao ser clicado.
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Limpa todos os campos e volta ao estado inicial.
                editTextWeight.setText("");
                seekBarHeight.setProgress(0);
                textViewResult.setText("");
                textViewResult.setVisibility(View.GONE);
                textViewHeight.setVisibility(View.GONE);
            }
        });

    }
}