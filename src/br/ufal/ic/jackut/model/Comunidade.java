package br.ufal.ic.jackut.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class Comunidade {

    private String nome;
    private String descricao;
    private String dono;
    private Set<String> membros = new LinkedHashSet<>();

    public Comunidade(String dono, String nome, String descricao) {
        this.dono = dono;
        this.nome = nome;
        this.descricao = descricao;
        this.membros.add(dono);
    }

    public Comunidade(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public Set<String> getMembros() {
        return new LinkedHashSet<>(membros);
    }

    public void setMembros(Set<String> membros) {
        this.membros = new LinkedHashSet<>(membros);
    }

    public String listarMembros() {
        return "{" + String.join(",", membros) + "}";
    }
}