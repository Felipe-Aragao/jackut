package br.ufal.ic.jackut.exception;

public class UsuarioNaoPodeSerInimigoDeSiMesmoException extends RelacionamentoException {

    public UsuarioNaoPodeSerInimigoDeSiMesmoException() {
        super("Usuário não pode ser inimigo de si mesmo.");
    }
}
