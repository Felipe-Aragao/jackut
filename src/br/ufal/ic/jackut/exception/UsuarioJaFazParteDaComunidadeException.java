package br.ufal.ic.jackut.exception;

public class UsuarioJaFazParteDaComunidadeException extends Exception {
    public UsuarioJaFazParteDaComunidadeException() {
        super("Usuario já faz parte dessa comunidade.");
    }
}