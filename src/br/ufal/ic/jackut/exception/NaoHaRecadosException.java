package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando não existem recados para o usuário.
 */
public class NaoHaRecadosException extends Exception {
    /**
     * Cria a exceção com a mensagem "N�o h� recados.".
     */
    public NaoHaRecadosException() {
        super("N�o h� recados.");
    }
}
