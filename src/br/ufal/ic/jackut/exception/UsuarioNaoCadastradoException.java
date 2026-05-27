package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando não se encontra um usuário no sistema.
 */
public class UsuarioNaoCadastradoException extends Exception{
    /**
     * Cria a exceção com a mensagem "Usu�rio n�o cadastrado.".
     */
    public UsuarioNaoCadastradoException() {
        super("Usu�rio n�o cadastrado.");
    }
}
