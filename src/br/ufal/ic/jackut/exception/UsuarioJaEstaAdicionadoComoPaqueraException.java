package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando o usuário já está adicionado como paquera.
 */
public class UsuarioJaEstaAdicionadoComoPaqueraException extends RelacionamentoException {

    /**
     * Cria a exceção com a mensagem "Usuário já está adicionado como paquera.".
     */
    public UsuarioJaEstaAdicionadoComoPaqueraException() {
        super("Usuário já está adicionado como paquera.");
    }
}
