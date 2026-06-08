package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando um atributo não está preenchido.
 */
public class AtributoNaoPreenchidoException extends Exception {
    /**
     * Cria a exceção com a mensagem "Atributo não preenchido.".
     */
    public AtributoNaoPreenchidoException() {
        super("Atributo não preenchido.");
    }
}
