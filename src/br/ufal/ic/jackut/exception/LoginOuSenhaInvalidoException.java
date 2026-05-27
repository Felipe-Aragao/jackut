package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando login e/ou senha é inválido ou não confere com
 * os dados cadastrados.
 */
public class LoginOuSenhaInvalidoException extends Exception{
    /**
     * Cria a exceção com a mensagem "Login ou senha inv�lidos."".
     */
    public LoginOuSenhaInvalidoException() {
        super("Login ou senha inv�lidos.");
    }
}
