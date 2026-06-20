package br.ufal.ic.jackut.exception;

/**
 * Exceção base para erros relacionados a relacionamentos entre usuários.
 */
public class RelacionamentoException extends Exception {

    /**
     * Cria a exceção com a mensagem informada.
     *
     * @param mensagem mensagem da exceção
     */
    public RelacionamentoException(String mensagem) {
        super(mensagem);
    }
}