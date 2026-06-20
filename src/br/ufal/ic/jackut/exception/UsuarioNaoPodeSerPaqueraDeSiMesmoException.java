package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando um usuário tenta ser paquera de si mesmo.
 */
public class UsuarioNaoPodeSerPaqueraDeSiMesmoException extends RelacionamentoException {

    /**
     * Cria a exceção com a mensagem "Usuário não pode ser paquera de si mesmo.".
     */
    public UsuarioNaoPodeSerPaqueraDeSiMesmoException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }
}
