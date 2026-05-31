package br.ufal.ic.jackut.model;

import br.ufal.ic.jackut.repository.UsuarioRepository;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Modelo que representa um usuário do sistema.
 * Contém `login`, `senha` e um mapa de atributos adicionais.
 */
public class Usuario {

    private String login;
    private String senha;

    private Map<String, String> atributos = new HashMap<>();

    private Set<String> amigos = new LinkedHashSet<>();
    private Set<String> convites = new LinkedHashSet<>();

    /**
     * Cria um usuário com login, senha e nome (armazenado em atributos).
     *
     * @param login login do usuário
     * @param senha senha do usuário
     * @param nome  nome do usuário
     */
    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;

        atributos.put("nome", nome);
    }

    /**
     * Construtor vazio necessário para serialização XML.
     */
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

    /**
     * Retorna uma cópia do mapa de atributos do usuário.
     *
     * @return cópia do mapa de atributos
     */
    public Map<String, String> getAtributos() {
        return new HashMap<>(atributos);
    }

    /**
     * Substitui o mapa de atributos do usuário.
     *
     * @param atributos novo mapa de atributos
     */
    public void setAtributos(Map<String, String> atributos) {
        this.atributos = atributos;
    }

    public Set<String> getAmigos() {
        return new LinkedHashSet<>(amigos);
    }

    public void setAmigos(Set<String> amigos) {
        this.amigos = new LinkedHashSet<>(amigos);
    }

    public Set<String> getConvites() {
        return new LinkedHashSet<>(convites);
    }

    public void setConvites(Set<String> convites) {
        this.convites = new LinkedHashSet<>(convites);
    }
}
