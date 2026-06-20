package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando o usuário tenta adicionar a si.
 */
public class UsuarioNaoPodeSeAutoAdicionarException extends RelacionamentoException {
    /**
     * Cria a exceção com a mensagem "Usuário não pode adicionar a si mesmo como amigo.".
     */
    public UsuarioNaoPodeSeAutoAdicionarException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
