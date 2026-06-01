package br.ufal.ic.jackut.model;

/**
 * Representa um recado trocado entre usuários do sistema.
 * Contém remetente, destinatário e o texto da mensagem.
 */
public class Recado {

    private String remetente;
    private String destinatario;
    private String mensagem;

    /**
     * Cria um recado com remetente, destinatário e mensagem.
     * @param remetente login do usuário que enviou o recado
     * @param destinatario login do usuário que receberá o recado
     * @param mensagem texto do recado
     */
    public Recado(String remetente, String destinatario, String mensagem) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.mensagem = mensagem;
    }

    /**
     * Construtor vazio para XMl*/
    public Recado(){}

    /** Retorna o login do remetente. */
    public String getRemetente() {
        return remetente;
    }

    /** Define o login do remetente. */
    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    /** Retorna o login do destinatário. */
    public String getDestinatario() {
        return destinatario;
    }

    /** Define o login do destinatário. */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /** Retorna o texto do recado. */
    public String getMensagem() {
        return mensagem;
    }

    /** Define o texto do recado. */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
