package br.com.felipekauadelima.rpgapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PersonagemAdapter extends BaseAdapter {

    private Context context;
    private List<Personagem> listaPersonagens;
    private String[] racas;
    private static class PersonagemHolder {
        public TextView textViewValorNome;
        public TextView textViewValorClasse;
        public TextView textViewValorNPC;
        public TextView textViewValorAlinhamento;
        public TextView textViewValorRaca;
    }

    public PersonagemAdapter(Context context, List<Personagem> listaPersonagens) {
        this.context = context;
        this.listaPersonagens = listaPersonagens;
        racas = context.getResources().getStringArray(R.array.raca_array);
    }

    @Override
    public int getCount() {
        return listaPersonagens.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPersonagens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PersonagemHolder holder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.linha_lista_personagens,parent,false);
            holder = new PersonagemHolder();
            holder.textViewValorNome = convertView.findViewById(R.id.textViewValorNome);
            holder.textViewValorClasse = convertView.findViewById(R.id.textViewValorClasse);
            holder.textViewValorNPC = convertView.findViewById(R.id.textViewValorNPC);
            holder.textViewValorAlinhamento = convertView.findViewById(R.id.textViewValorAlinhamento);
            holder.textViewValorRaca = convertView.findViewById(R.id.textViewValorRaca);
            convertView.setTag(holder);
        }else{
            holder = (PersonagemHolder) convertView.getTag();
        }

        Personagem personagem = listaPersonagens.get(position);
        holder.textViewValorNome.setText(personagem.getNome());
        holder.textViewValorClasse.setText(personagem.getClasse());

        if (personagem.isNPC()){
            holder.textViewValorNPC.setText(R.string.npc);
        }else{
            holder.textViewValorNPC.setText(R.string.nao_npc);
        }

        switch (personagem.getAlinhamento()){
            case Bom:
                holder.textViewValorAlinhamento.setText(R.string.bom);
                break;
            case Neutro:
                holder.textViewValorAlinhamento.setText(R.string.neutro);
                break;
            case Mau:
                holder.textViewValorAlinhamento.setText(R.string.mau);
                break;
        }

        holder.textViewValorRaca.setText(racas[personagem.getRaca()]);

        return convertView;
    }
}
