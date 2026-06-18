package br.ufal.ic.jackut.exception;

public class ComunidadeNaoExisteException extends Exception {
    public ComunidadeNaoExisteException() {
        super("Comunidade não existe.");
    }
}
