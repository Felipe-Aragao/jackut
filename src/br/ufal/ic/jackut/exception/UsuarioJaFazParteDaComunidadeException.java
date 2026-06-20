package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando o usuário já faz parte da comunidade.
 */
public class UsuarioJaFazParteDaComunidadeException extends Exception {
    /**
     * Cria a exceção com a mensagem "Usuario já faz parte dessa comunidade.".
     */
    public UsuarioJaFazParteDaComunidadeException() {
        super("Usuario já faz parte dessa comunidade.");
    }
}