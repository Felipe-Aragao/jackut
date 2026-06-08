package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando uma senha fornecida é inválida (nula ou vazia).
 */
public class SenhaInvalidaException extends Exception {
    /**
     * Cria a exceção com a mensagem "Senha inválida.".
     */
    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}
