package br.ufal.ic.jackut.exception;

public class UsuarioNaoCadastradoException extends Exception{
    public UsuarioNaoCadastradoException() {
        super("Usu�rio n�o cadastrado.");
    }
}
