package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando já existe convite esperando ser aceito.
 */
public class EsperandoAceitacaoDoConviteException extends Exception {
    /**
     * Cria a exceção com mensagem "Usuário já está adicionado como amigo, esperando aceitação do convite.".
     */
    public EsperandoAceitacaoDoConviteException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}
