package br.ufal.ic.jackut.exception;

/**
 * Exceção lançada quando o usuário já está adicionado como inimigo.
 */
public class UsuarioJaEstaAdicionadoComoInimigoException extends RelacionamentoException {

    /**
     * Cria a exceção com a mensagem "Usuário já está adicionado como inimigo.".
     */
    public UsuarioJaEstaAdicionadoComoInimigoException() {
        super("Usuário já está adicionado como inimigo.");
    }
}
