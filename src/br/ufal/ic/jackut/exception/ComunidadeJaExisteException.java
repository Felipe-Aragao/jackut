package br.ufal.ic.jackut.exception;

public class ComunidadeJaExisteException extends Exception {
    public ComunidadeJaExisteException() {
        super("Comunidade com esse nome já existe.");
    }
}
