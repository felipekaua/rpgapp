package br.com.felipekauadelima.rpgapp;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PersonagemActivity extends AppCompatActivity {

    private EditText editTextNome, editTextClasse;
    private CheckBox checkBoxNpc;
    private RadioGroup radioGroupAlinhamento;
    private Spinner spinnerRaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personagem);

        editTextNome = findViewById(R.id.editTextNome);
        editTextClasse = findViewById(R.id.editTextClasse);
        checkBoxNpc = findViewById(R.id.checkBoxNpc);
        radioGroupAlinhamento = findViewById(R.id.radioGroupAlinhamento);
        spinnerRaca = findViewById(R.id.spinnerRaca);

//        popularSpinner();
    }

//    private void popularSpinner(){
//        ArrayList<String> lista = new ArrayList<>();
//        lista.add(getString(R.string.humano));
//        lista.add(getString(R.string.anao));
//        lista.add(getString(R.string.elfo));
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
//        spinnerRaca.setAdapter(adapter);
//    }

    public void limparCampos(View view){
        editTextNome.setText(null);
        editTextClasse.setText(null);
        checkBoxNpc.setChecked(false);
        radioGroupAlinhamento.clearCheck();
        spinnerRaca.setSelection(0);
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

        boolean npc = checkBoxNpc.isChecked();

        int radioButtonId = radioGroupAlinhamento.getCheckedRadioButtonId();

        String alinhamento;

        if(radioButtonId == R.id.radioButtonBom){
            alinhamento = getString(R.string.bom);
        }else if(radioButtonId == R.id.radioButtonMau) {
            alinhamento = getString(R.string.mau);
        }else if(radioButtonId == R.id.radioButtonNeutro){
            alinhamento = getString(R.string.neutro);
        }else{
            Toast.makeText(this, R.string.selecao, Toast.LENGTH_SHORT).show();
            radioGroupAlinhamento.requestFocus();
            return;
        }

        String raca = (String) spinnerRaca.getSelectedItem();

        if(raca == null){
            Toast.makeText(this, R.string.spinner, Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, getString(R.string.nome_valor)+nome+"\n"+
                                         getString(R.string.classe_valor)+classe+"\n"+
                                         (npc ? getString(R.string.npc) : getString(R.string.nao_npc))+"\n"+
                                         getString(R.string.alinhamento_valor)+alinhamento+"\n"+
                                         getString(R.string.raca_valor)+raca,
                                         Toast.LENGTH_LONG).show();

    }
}