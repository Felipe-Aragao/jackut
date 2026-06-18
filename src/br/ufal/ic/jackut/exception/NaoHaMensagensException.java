package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando não existem mensagens para o usuário.
 */
public class NaoHaMensagensException extends Exception {
    /**
     * Cria a exceção com a mensagem "Não há mensagens.".
     */
    public NaoHaMensagensException() {
        super("Não há mensagens.");
    }
}