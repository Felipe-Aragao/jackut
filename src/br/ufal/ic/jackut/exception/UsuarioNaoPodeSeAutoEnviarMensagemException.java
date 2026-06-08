package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando um usuário tenta enviar um recado para si próprio.
 */
public class UsuarioNaoPodeSeAutoEnviarMensagemException extends Exception {
    /**
     * Cria a exceção com a mensagem "Usuário não pode enviar recado para si mesmo.".
     */
    public UsuarioNaoPodeSeAutoEnviarMensagemException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}
