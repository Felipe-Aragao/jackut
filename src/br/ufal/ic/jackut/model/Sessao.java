package br.ufal.ic.jackut.model;

import java.util.UUID;

/**
 * Representa uma sessão de usuário no sistema. A sessão possui um identificador
 * único (`id`) gerado aleatoriamente e referência ao `Usuario` associado.
 */
public class Sessao {

    private String id;
    private Usuario usuario;

    /**
     * Cria uma nova sessão para o usuário informado, gerando um `id` único.
     *
     * @param usuario o usuário associado à sessão
     */
    public Sessao(Usuario usuario) {
        this.id = UUID.randomUUID().toString();
        this.usuario = usuario;
    }

    /**
     * Retorna o identificador da sessão.
     *
     * @return identificador da sessão
     */
    public String getId() {
        return id;
    }

    /**
     * Define o identificador da sessão.
     *
     * @param id identificador da sessão
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retorna o usuário associado a sessão.
     *
     * @return usuário associado
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário associado a sessão.
     *
     * @param usuario usuário associado
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
