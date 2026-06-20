package br.ufal.ic.jackut.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Modelo que representa uma comunidade do sistema.
 * Contem dono, nome e descrição da comunidade.
 */
public class Comunidade {

    private String nome;
    private String descricao;
    private String dono;

    /**
     * Cria uma comunidade com dono, nome e descrição.
     *
     * @param dono login do usuário dono da comunidade
     * @param nome nome da comunidade
     * @param descricao descrição da comunidade
     */
    public Comunidade(String dono, String nome, String descricao) {
        this.dono = dono;
        this.nome = nome;
        this.descricao = descricao;
    }

    /**
     * Construtor vazio necessário para serialização XML.
     */
    public Comunidade(){}

    /**
     * Retorna o nome da comunidade.
     *
     * @return nome da comunidade
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da comunidade.
     *
     * @param nome nome da comunidade
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a descrição da comunidade.
     *
     * @return descrição da comunidade
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição da comunidade.
     *
     * @param descricao descrição da comunidade
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o login do dono da comunidade.
     *
     * @return login do dono
     */
    public String getDono() {
        return dono;
    }

    /**
     * Define o login do dono da comunidade.
     *
     * @param dono login do dono
     */
    public void setDono(String dono) {
        this.dono = dono;
    }

    /**
     * Retorna os membros da comunidade.
     *
     * @return conjunto de membros
     */
    public Set<String> getMembros() {
        return new LinkedHashSet<>();
    }
}