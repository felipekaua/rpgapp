package br.com.felipekauadelima.rpgapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PersonagemActivity extends AppCompatActivity {

    private EditText editTextNome, editTextClasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personagem);

        editTextNome = findViewById(R.id.editTextNome);
        editTextClasse = findViewById(R.id.editTextClasse);
    }

    public void limparCampos(View view){
        editTextNome.setText(null);
        editTextClasse.setText(null);
        editTextNome.requestFocus();
        Toast.makeText(this, R.string.as_entradas_foram_apagadas, Toast.LENGTH_SHORT).show();
    }

    public void salvarCampos(View view){
        String nome = editTextNome.getText().toString();

        if(nome == null || nome.trim().isEmpty()){
            Toast.makeText(this, R.string.o_campo_nome_esta_vazio, Toast.LENGTH_SHORT).show();
            editTextNome.requestFocus();
            return;
        }

        nome = nome.trim();

        String classe = editTextClasse.getText().toString();

        if(classe == null || classe.trim().isEmpty()){
            Toast.makeText(this, R.string.o_campo_classe_esta_vazio, Toast.LENGTH_SHORT).show();
            editTextClasse.requestFocus();
            return;
        }

        classe = classe.trim();

        Toast.makeText(this, getString(R.string.nome_valor)+nome+"\n"+getString(R.string.classe_valor) + classe, Toast.LENGTH_LONG).show();

    }
}