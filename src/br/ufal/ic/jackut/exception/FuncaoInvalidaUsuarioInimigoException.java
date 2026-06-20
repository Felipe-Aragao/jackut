package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando uma função não pode ser executada para um usuário inimigo.
 */
public class FuncaoInvalidaUsuarioInimigoException extends RelacionamentoException {

    /**
     * Cria a exceção indicando o usuário inimigo envolvido.
     *
     * @param nomeUsuario nome do usuário inimigo
     */
    public FuncaoInvalidaUsuarioInimigoException(String nomeUsuario) {
        super("Função inválida: " + nomeUsuario + " é seu inimigo.");
    }
}
