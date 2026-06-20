package br.ufal.ic.jackut.model;

/**
 * Representa a participação de um usuário em uma comunidade.
 * Contem o login do usuário e o nome da comunidade.
 */
public class ParticipacaoComunidade {

    private String login;
    private String comunidade;

    /**
     * Cria uma participação de usuário em comunidade.
     *
     * @param login login do usuário participante
     * @param comunidade nome da comunidade
     */
    public ParticipacaoComunidade(String login, String comunidade) {
        this.login = login;
        this.comunidade = comunidade;
    }

    /**
     * Construtor vazio necessário para serialização XML.
     */
    public ParticipacaoComunidade() {}

    /**
     * Retorna o login do usuário participante.
     *
     * @return login do usuário
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define o login do usuário participante.
     *
     * @param login login do usuário
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Retorna o nome da comunidade.
     *
     * @return nome da comunidade
     */
    public String getComunidade() {
        return comunidade;
    }

    /**
     * Define o nome da comunidade.
     *
     * @param comunidade nome da comunidade
     */
    public void setComunidade(String comunidade) {
        this.comunidade = comunidade;
    }
}