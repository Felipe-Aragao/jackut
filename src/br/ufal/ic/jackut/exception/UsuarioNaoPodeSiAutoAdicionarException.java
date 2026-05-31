package br.ufal.ic.jackut.exception;

public class UsuarioNaoPodeSiAutoAdicionarException extends Exception {
    public UsuarioNaoPodeSiAutoAdicionarException() {
        super("Usu�rio n�o pode adicionar a si mesmo como amigo.");
    }
}
