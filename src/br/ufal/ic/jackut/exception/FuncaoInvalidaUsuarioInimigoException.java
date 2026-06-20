package br.ufal.ic.jackut.exception;

public class FuncaoInvalidaUsuarioInimigoException extends RelacionamentoException {

    public FuncaoInvalidaUsuarioInimigoException(String nomeUsuario) {
        super("Função inválida: " + nomeUsuario + " é seu inimigo.");
    }
}
