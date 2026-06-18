package br.ufal.ic.jackut.model;

public class ParticipacaoComunidade {

    private String login;
    private String comunidade;

    public ParticipacaoComunidade(String login, String comunidade) {
        this.login = login;
        this.comunidade = comunidade;
    }

    public ParticipacaoComunidade() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getComunidade() {
        return comunidade;
    }

    public void setComunidade(String comunidade) {
        this.comunidade = comunidade;
    }
}