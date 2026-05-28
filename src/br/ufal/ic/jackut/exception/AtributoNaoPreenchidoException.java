package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando um atributo não está preenchido.
 */
public class AtributoNaoPreenchidoException extends Exception {
    /**
     * Cria a exceção com a mensagem "Atributo n�o preenchido.".
     */
    public AtributoNaoPreenchidoException() {
        super("Atributo n�o preenchido.");
    }
}
