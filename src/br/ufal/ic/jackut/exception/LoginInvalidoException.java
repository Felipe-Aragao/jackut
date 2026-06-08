package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando um login fornecido é inválido (nulo ou vazio).
 */
public class LoginInvalidoException extends Exception {
    /**
     * Cria a exceção com a mensagem "Login inválido.".
     */
    public LoginInvalidoException() {
        super("Login inválido.");
    }
}
