package br.ufal.ic.jackut.exception;

public class UsuarioNaoPodeSerInimigoDeSiMesmoException extends Exception {

    public UsuarioNaoPodeSerInimigoDeSiMesmoException() {
        super("Usuário não pode ser inimigo de si mesmo.");
    }
}
