package br.ufal.ic.jackut.model;

public class Mensagem {

    private String remetente;
    private String texto;

    public Mensagem(String remetente, String texto) {
        this.remetente = remetente;
        this.texto = texto;
    }

    public Mensagem() {}

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
