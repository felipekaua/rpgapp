package br.com.felipekauadelima.rpgapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PersonagemActivity extends AppCompatActivity {

    public static final String KEY_NOME = "KEY_NOME";
    public static final String KEY_CLASSE = "KEY_CLASSE";
    public static final String KEY_NPC = "KEY_NPC";
    public static final String KEY_RACA = "KEY_RACA";
    public static final String KEY_ALINHAMENTO = "KEY_ALINHAMENTO";
    public static final String KEY_MODO = "MODO";
    public static final int MODO_NOVO = 0;
    public static final int MODO_EDITAR = 1;
    private EditText editTextNome, editTextClasse;
    private CheckBox checkBoxNpc;
    private RadioGroup radioGroupAlinhamento;
    private RadioButton radioButtonBom;
    private RadioButton radioButtonNeutro;
    private RadioButton radioButtonMau;
    private Spinner spinnerRaca;
    private int modo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personagem);

        editTextNome = findViewById(R.id.editTextNome);
        editTextClasse = findViewById(R.id.editTextClasse);
        checkBoxNpc = findViewById(R.id.checkBoxNpc);
        radioGroupAlinhamento = findViewById(R.id.radioGroupAlinhamento);
        spinnerRaca = findViewById(R.id.spinnerRaca);
        radioButtonBom = findViewById(R.id.radioButtonBom);
        radioButtonNeutro = findViewById(R.id.radioButtonNeutro);
        radioButtonMau = findViewById(R.id.radioButtonMau);

        Intent intentAbertura = getIntent();
        Bundle bundle = intentAbertura.getExtras();

        if(bundle!=null){
            modo = bundle.getInt(KEY_MODO);

            if(modo == MODO_NOVO){
                setTitle(getString(R.string.novo_personagem));

            }else{
                setTitle(getString(R.string.editar_pessoa));

                String nome = bundle.getString(PersonagemActivity.KEY_NOME);
                String classe = bundle.getString(PersonagemActivity.KEY_CLASSE);
                Boolean npc = bundle.getBoolean(PersonagemActivity.KEY_NPC);
                String alinhamentoTexto = bundle.getString(PersonagemActivity.KEY_ALINHAMENTO);
                int raca = bundle.getInt(PersonagemActivity.KEY_RACA);

                Alinhamento alinhamento = Alinhamento.valueOf(alinhamentoTexto);

                editTextNome.setText(nome);
                editTextClasse.setText(classe);
                checkBoxNpc.setChecked(npc);
                spinnerRaca.setSelection(raca);

                if(alinhamento == Alinhamento.Bom){
                    radioButtonBom.setChecked(true);
                }else if(alinhamento == Alinhamento.Neutro){
                    radioButtonNeutro.setChecked(true);
                }else if(alinhamento == Alinhamento.Mau){
                    radioButtonMau.setChecked(true);
                }

            }
        }

    }

    public void limparCampos(){
        editTextNome.setText(null);
        editTextClasse.setText(null);
        checkBoxNpc.setChecked(false);
        radioGroupAlinhamento.clearCheck();
        spinnerRaca.setSelection(0);
        editTextNome.requestFocus();
        Toast.makeText(this, R.string.as_entradas_foram_apagadas, Toast.LENGTH_SHORT).show();
    }

    public void salvarCampos(){
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

        Alinhamento alinhamento;

        if(radioButtonId == R.id.radioButtonBom){
            alinhamento = Alinhamento.Bom;
        }else if(radioButtonId == R.id.radioButtonMau) {
            alinhamento = Alinhamento.Mau;
        }else if(radioButtonId == R.id.radioButtonNeutro){
            alinhamento = Alinhamento.Neutro;
        }else{
            Toast.makeText(this, R.string.selecao, Toast.LENGTH_SHORT).show();
            radioGroupAlinhamento.requestFocus();
            return;
        }

        int raca = spinnerRaca.getSelectedItemPosition();

        if(raca == AdapterView.INVALID_POSITION){
            Toast.makeText(this, R.string.spinner, Toast.LENGTH_LONG).show();
            return;
        }

        Intent intentResposta = new Intent();
        intentResposta.putExtra(KEY_NOME, nome);
        intentResposta.putExtra(KEY_CLASSE, classe);
        intentResposta.putExtra(KEY_NPC, npc);
        intentResposta.putExtra(KEY_RACA, raca);
        intentResposta.putExtra(KEY_ALINHAMENTO, alinhamento.toString());

        setResult(PersonagemActivity.RESULT_OK, intentResposta);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.personagem_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idMenuItem = item.getItemId();

        if(idMenuItem==R.id.menuItemSalvar){
            salvarCampos();
            return true;
        }else{
            if (idMenuItem==R.id.menuItemLimpar){
                limparCampos();
                return true;
            }else{
                return super.onOptionsItemSelected(item);
            }
        }
    }
}