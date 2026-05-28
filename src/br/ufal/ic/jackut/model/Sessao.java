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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
