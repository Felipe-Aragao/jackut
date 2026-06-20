package br.ufal.ic.jackut.exception;

public class UsuarioJaEstaAdicionadoComoPaqueraException extends RelacionamentoException {

    public UsuarioJaEstaAdicionadoComoPaqueraException() {
        super("Usuário já está adicionado como paquera.");
    }
}
