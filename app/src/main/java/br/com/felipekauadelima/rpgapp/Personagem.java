package br.com.felipekauadelima.rpgapp;

public class Personagem {

    private String nome;
    private String classe;
    private Boolean NPC;
    private Alinhamento alinhamento;
    private int raca;

    public Personagem(String nome, String classe, Boolean NPC, Alinhamento alinhamento, int raca) {
        this.nome = nome;
        this.classe = classe;
        this.NPC = NPC;
        this.alinhamento = alinhamento;
        this.raca = raca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Boolean isNPC() {
        return NPC;
    }

    public void setNPC(Boolean NPC) {
        this.NPC = NPC;
    }

    public Alinhamento getAlinhamento() {
        return alinhamento;
    }

    public void setAlinhamento(Alinhamento alinhamento) {
        this.alinhamento = alinhamento;
    }

    public int getRaca() {
        return raca;
    }

    public void setRaca(int raca) {
        this.raca = raca;
    }

    @Override
    public String toString() {
        return
                nome        + '\n' +
                classe      + '\n' +
                NPC         + '\n' +
                alinhamento + '\n' +
                raca;
    }
}
