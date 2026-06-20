package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando um usuário tenta ser fa de si mesmo.
 */
public class UsuarioNaoPodeSerFaDeSiMesmoException extends RelacionamentoException {

    /**
     * Cria a exceção com a mensagem "Usuário não pode ser fa de si mesmo.".
     */
    public UsuarioNaoPodeSerFaDeSiMesmoException() {
        super("Usuário não pode ser fã de si mesmo.");
    }
}
