package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando não se encontra um usuário no sistema.
 */
public class UsuarioNaoCadastradoException extends Exception{
    /**
     * Cria a exceção com a mensagem "Usuário não cadastrado.".
     */
    public UsuarioNaoCadastradoException() {
        super("Usuário não cadastrado.");
    }
}
