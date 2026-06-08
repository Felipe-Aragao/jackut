package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando o usuário já é amigo.
 */
public class UsuarioJaEstaAdicionadoComoAmigoException extends Exception {
    /**
     * Cria a exceção com a mensagem "Usuário já está adicionado como amigo.".
     */
    public UsuarioJaEstaAdicionadoComoAmigoException() {
        super("Usuário já está adicionado como amigo.");
    }
}
