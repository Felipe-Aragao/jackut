package br.ufal.ic.jackut.exception;

public class UsuarioNaoPodeSeAutoAdicionarException extends Exception {
    public UsuarioNaoPodeSeAutoAdicionarException() {
        super("Usu�rio n�o pode adicionar a si mesmo como amigo.");
    }
}
