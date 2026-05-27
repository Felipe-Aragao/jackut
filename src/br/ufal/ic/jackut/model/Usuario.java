package br.ufal.ic.jackut.model;

import java.util.HashMap;
import java.util.Map;

public class Usuario {

    private String login;
    private String senha;

    private Map<String, String> atributos;

    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.atributos = new HashMap<>();

        atributos.put("nome", nome);
    }

    public Usuario(){}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Map<String, String> getAtributos() {
        return atributos;
    }

    public void setAtributos(Map<String, String> atributos) {
        this.atributos = atributos;
    }
}
