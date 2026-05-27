package br.ufal.ic.jackut.exception;

public class LoginOuSenhaInvalidoException extends Exception{
    public LoginOuSenhaInvalidoException() {
        super("Login ou senha inv�lidos.");
    }
}
