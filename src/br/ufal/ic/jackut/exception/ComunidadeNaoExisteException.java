package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando a comunidade informada não existe.
 */
public class ComunidadeNaoExisteException extends Exception {
    /**
     * Cria a exceção com a mensagem "Comunidade não existe.".
     */
    public ComunidadeNaoExisteException() {
        super("Comunidade não existe.");
    }
}
