package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.FuncaoInvalidaUsuarioInimigoException;
import br.ufal.ic.jackut.exception.RelacionamentoException;
import br.ufal.ic.jackut.model.Usuario;

/**
 * Classe base com validações comuns para estratégias de relacionamento.
 */
public abstract class AbstractRelacionamentoStrategy implements RelacionamentoStrategy {

    /**
     * Inicializa a estratégia base de relacionamento.
     */
    protected AbstractRelacionamentoStrategy() {}
    /**
     * Valida se o usuário de origem não está tentando se relacionar consigo mesmo.
     *
     * @param origem usuário que inicia o relacionamento
     * @param alvo usuário alvo do relacionamento
     * @param excecao exceção lançada quando origem e alvo são o mesmo usuário
     * @param <T> tipo específico da exceção de relacionamento
     * @throws T se origem e alvo tiverem o mesmo login
     */
    protected <T extends RelacionamentoException> void validarAutoRelacionamento(Usuario origem, Usuario alvo, T excecao)
            throws T {
        if (origem.getLogin().equals(alvo.getLogin())) {
            throw excecao;
        }
    }

    /**
     * Valida se o alvo não marcou a origem como inimiga.
     *
     * @param origem usuário que inicia o relacionamento
     * @param alvo usuário alvo do relacionamento
     * @throws FuncaoInvalidaUsuarioInimigoException se o alvo tiver a origem como inimiga
     */
    protected void validarBloqueioPorInimigo(Usuario origem, Usuario alvo)
            throws FuncaoInvalidaUsuarioInimigoException {
        if (alvo.temRelacionamento(Usuario.REL_INIMIGOS, origem.getLogin())) {
            throw new FuncaoInvalidaUsuarioInimigoException(alvo.getAtributo("nome"));
        }
    }
}
