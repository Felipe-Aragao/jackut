package br.ufal.ic.jackut.exception;

public class UsuarioJaEstaAdicionadoComoIdoloException extends RelacionamentoException {

    public UsuarioJaEstaAdicionadoComoIdoloException() {
        super("Usuário já está adicionado como ídolo.");
    }
}
