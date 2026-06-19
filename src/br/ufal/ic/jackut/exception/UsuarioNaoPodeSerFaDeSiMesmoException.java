package br.ufal.ic.jackut.exception;

public class UsuarioNaoPodeSerFaDeSiMesmoException extends Exception {

    public UsuarioNaoPodeSerFaDeSiMesmoException() {
        super("Usuário não pode ser fã de si mesmo.");
    }
}
