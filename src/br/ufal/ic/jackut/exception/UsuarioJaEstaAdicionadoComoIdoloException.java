package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando o usuário já está adicionado como ídolo.
 */
public class UsuarioJaEstaAdicionadoComoIdoloException extends RelacionamentoException {

    /**
     * Cria a exceção com a mensagem "Usuário já está adicionado como ídolo.".
     */
    public UsuarioJaEstaAdicionadoComoIdoloException() {
        super("Usuário já está adicionado como ídolo.");
    }
}
