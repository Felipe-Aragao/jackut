package br.ufal.ic.jackut.exception;

public class UsuarioJaEstaAdicionadoComoInimigoException extends RelacionamentoException {

    public UsuarioJaEstaAdicionadoComoInimigoException() {
        super("Usuário já está adicionado como inimigo.");
    }
}
