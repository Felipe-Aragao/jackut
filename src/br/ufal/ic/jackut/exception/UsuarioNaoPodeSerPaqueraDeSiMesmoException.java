package br.ufal.ic.jackut.exception;

public class UsuarioNaoPodeSerPaqueraDeSiMesmoException extends Exception {

    public UsuarioNaoPodeSerPaqueraDeSiMesmoException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }
}
