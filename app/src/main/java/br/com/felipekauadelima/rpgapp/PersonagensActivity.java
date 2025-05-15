package br.com.felipekauadelima.rpgapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PersonagensActivity extends AppCompatActivity {

    private ListView listViewPersonagens;

    private List<Personagem> listaPersonagens;
    private PersonagemAdapter personagemAdapter;

    private int posicaoSelecionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personagens);

        setTitle(getString(R.string.listagem_de_personagens));

        listViewPersonagens = findViewById(R.id.listViewPersonagens);

        listViewPersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Personagem personagem = (Personagem) listViewPersonagens.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), getString(R.string.personagem_de_nome) + personagem.getNome() + getString(R.string.foi_clicado), Toast.LENGTH_LONG).show();

            }
        });
        popularlistaPersonagens();
    }

    private void popularlistaPersonagens(){

//        String[] personagens_nomes = getResources().getStringArray(R.array.personagens_nome);
//        String[] personagens_classes = getResources().getStringArray(R.array.personagens_classe);
//        int[] personagens_npc = getResources().getIntArray(R.array.personagens_npc);
//        int[] personagens_alinhamentos = getResources().getIntArray(R.array.personagens_alinhamento);
//        int[] personagens_racas = getResources().getIntArray(R.array.personagens_raca);

        listaPersonagens = new ArrayList<>();

//        Personagem personagem;
//        boolean npc;
//        Alinhamento alinhamento;
//
//        Alinhamento[] alinhamentos = Alinhamento.values();
//
//        for (int cont=0;cont<personagens_nomes.length;cont++){
//
//            npc = (personagens_npc[cont]==1?true:false);
//            alinhamento = alinhamentos[personagens_alinhamentos[cont]];
//
//            personagem = new Personagem(
//                        personagens_nomes[cont],
//                        personagens_classes[cont],
//                        npc,
//                        alinhamento,
//                        personagens_racas[cont]);
//            listaPersonagens.add(personagem);
//        }

        personagemAdapter = new PersonagemAdapter(this,listaPersonagens);

        listViewPersonagens.setAdapter(personagemAdapter);

        registerForContextMenu(listViewPersonagens);
    }

    public void abrirSobre(){
        Intent intentAbertura = new Intent(this, SobreActivity.class);
        startActivity(intentAbertura);
    }

    ActivityResultLauncher<Intent> launcherNovoPersonagem = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if(result.getResultCode() == PersonagemActivity.RESULT_OK){
             Intent intent = result.getData();
             Bundle bundle = intent.getExtras();
             if(bundle != null){
                String nome = bundle.getString(PersonagemActivity.KEY_NOME);
                String classe = bundle.getString(PersonagemActivity.KEY_CLASSE);
                Boolean npc = bundle.getBoolean(PersonagemActivity.KEY_NPC);
                String alinhamento = bundle.getString(PersonagemActivity.KEY_ALINHAMENTO);
                int raca = bundle.getInt(PersonagemActivity.KEY_RACA);

                Personagem personagem = new Personagem(nome, classe, npc, Alinhamento.valueOf(alinhamento), raca);

                listaPersonagens.add(personagem);

                personagemAdapter.notifyDataSetChanged();
             }
            }
        }
    });

    public void abrirAdicionar(){
        Intent intentAbertura = new Intent(this, PersonagemActivity.class);
        intentAbertura.putExtra(PersonagemActivity.KEY_MODO, PersonagemActivity.MODO_NOVO);
        launcherNovoPersonagem.launch(intentAbertura);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.personagens_opcoes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idMenuItem = item.getItemId();

        if(idMenuItem== R.id.menuItemAdicionar){
            abrirAdicionar();
            return true;
        }else{
            if(idMenuItem== R.id.menuItemSobre){
                abrirSobre();
                return true;
            }else{
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.personagens_item_selecionado,menu);
    }

    private void excluirPersonagem(int posicao){
        listaPersonagens.remove(posicao);
        personagemAdapter.notifyDataSetChanged();
    }

    private void editarPersonagem(int posicao){
        posicaoSelecionada = posicao;

        Personagem personagem = listaPersonagens.get(posicaoSelecionada);
        Intent intentAbertura = new Intent(this, PersonagemActivity.class);
        intentAbertura.putExtra(PersonagemActivity.KEY_MODO, PersonagemActivity.MODO_EDITAR);
        intentAbertura.putExtra(PersonagemActivity.KEY_NOME, personagem.getNome());
        intentAbertura.putExtra(PersonagemActivity.KEY_CLASSE, personagem.getClasse());
        intentAbertura.putExtra(PersonagemActivity.KEY_NPC, personagem.isNPC());
        intentAbertura.putExtra(PersonagemActivity.KEY_ALINHAMENTO, personagem.getAlinhamento().toString());
        intentAbertura.putExtra(PersonagemActivity.KEY_RACA, personagem.getRaca());

        launcherEditarPersonagem.launch(intentAbertura);
    }

    ActivityResultLauncher<Intent> launcherEditarPersonagem = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if(result.getResultCode() == PersonagemActivity.RESULT_OK){
                Intent intent = result.getData();
                Bundle bundle = intent.getExtras();
                if(bundle != null){
                    String nome = bundle.getString(PersonagemActivity.KEY_NOME);
                    String classe = bundle.getString(PersonagemActivity.KEY_CLASSE);
                    Boolean npc = bundle.getBoolean(PersonagemActivity.KEY_NPC);
                    String alinhamentoTexto = bundle.getString(PersonagemActivity.KEY_ALINHAMENTO);
                    int raca = bundle.getInt(PersonagemActivity.KEY_RACA);

                    Personagem personagem = listaPersonagens.get(posicaoSelecionada);

                    Alinhamento alinhamento = Alinhamento.valueOf(alinhamentoTexto);

                    personagem.setNome(nome);
                    personagem.setClasse(classe);
                    personagem.setNPC(npc);
                    personagem.setAlinhamento(alinhamento);
                    personagem.setRaca(raca);

                    posicaoSelecionada = -1;

                    personagemAdapter.notifyDataSetChanged();
                }
            }
        }
    });

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int idMenuItem = item.getItemId();

        if(idMenuItem == R.id.menuItemEditar){
            editarPersonagem(info.position);
            return true;
        }else{
            if(idMenuItem == R.id.menuItemExcluir){
                excluirPersonagem(info.position);
                return true;
            }else{
                return super.onContextItemSelected(item);
            }
        }
    }
}