package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando o usuário já é amigo.
 */
public class UsuarioJaEstaAdicionadoComoAmigoException extends RelacionamentoException {
    /**
     * Cria a exceção com a mensagem "Usuário já está adicionado como amigo.".
     */
    public UsuarioJaEstaAdicionadoComoAmigoException() {
        super("Usuário já está adicionado como amigo.");
    }
}
