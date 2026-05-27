package br.ufal.ic.jackut.model;

import java.util.UUID;

public class Sessao {

    private String id;
    private Usuario usuario;

    public Sessao(Usuario usuario) {
        this.id = UUID.randomUUID().toString();
        this.usuario = usuario;
    }



}
