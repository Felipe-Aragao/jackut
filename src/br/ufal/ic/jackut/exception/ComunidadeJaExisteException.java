package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando já existe uma comunidade com o nome informado.
 */
public class ComunidadeJaExisteException extends Exception {
    /**
     * Cria a exceção com a mensagem "Comunidade com esse nome já existe.".
     */
    public ComunidadeJaExisteException() {
        super("Comunidade com esse nome já existe.");
    }
}
