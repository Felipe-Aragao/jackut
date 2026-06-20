package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando um usuário tenta ser inimigo de si mesmo.
 */
public class UsuarioNaoPodeSerInimigoDeSiMesmoException extends RelacionamentoException {

    /**
     * Cria a exceção com a mensagem "Usuário não pode ser inimigo de si mesmo.".
     */
    public UsuarioNaoPodeSerInimigoDeSiMesmoException() {
        super("Usuário não pode ser inimigo de si mesmo.");
    }
}
