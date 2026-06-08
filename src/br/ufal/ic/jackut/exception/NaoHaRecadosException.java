package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando não existem recados para o usuário.
 */
public class NaoHaRecadosException extends Exception {
    /**
     * Cria a exceção com a mensagem "Não há recados.".
     */
    public NaoHaRecadosException() {
        super("Não há recados.");
    }
}
