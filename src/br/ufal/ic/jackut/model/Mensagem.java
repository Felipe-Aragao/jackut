package br.ufal.ic.jackut.model;

/**
 * Representa uma mensagem enviada entre usuários do sistema.
 * Contem o remetente e o texto da mensagem.
 */
public class Mensagem {

    private String remetente;
    private String texto;

    /**
     * Cria uma mensagem com remetente e texto.
     *
     * @param remetente login do usuário que enviou a mensagem
     * @param texto texto da mensagem
     */
    public Mensagem(String remetente, String texto) {
        this.remetente = remetente;
        this.texto = texto;
    }

    /**
     * Construtor vazio necessário para serialização XML.
     */
    public Mensagem() {}

    /**
     * Retorna o login do remetente.
     *
     * @return login do remetente
     */
    public String getRemetente() {
        return remetente;
    }

    /**
     * Define o login do remetente.
     *
     * @param remetente login do remetente
     */
    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    /**
     * Retorna o texto da mensagem.
     *
     * @return texto da mensagem
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Define o texto da mensagem.
     *
     * @param texto texto da mensagem
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }
}
