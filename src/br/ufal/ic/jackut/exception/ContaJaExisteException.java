package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando se tenta criar uma conta cujo login já existe.
 */
public class ContaJaExisteException extends Exception {
    /**
     * Cria a exceção com mensagem "Conta com esse nome já existe.".
     */
    public ContaJaExisteException() {
        super("Conta com esse nome já existe.");
    }
}
