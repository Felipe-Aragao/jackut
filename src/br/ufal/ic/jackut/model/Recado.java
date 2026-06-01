package br.ufal.ic.jackut.model;

public class Recado {

    private String remetente;
    private String destinatario;
    private String mensagem;

    public Recado(String remetente, String destinatario, String mensagem) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.mensagem = mensagem;
    }

    public Recado(){}

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
