package br.ufal.ic.jackut.exception;

public class ContaJaExisteException extends Exception {
    public ContaJaExisteException() {
        super("Conta com esse nome j� existe.");
    }
}
