package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.FuncaoInvalidaUsuarioInimigoException;
import br.ufal.ic.jackut.exception.RelacionamentoException;
import br.ufal.ic.jackut.model.Usuario;

public abstract class AbstractRelacionamentoStrategy implements RelacionamentoStrategy {

    protected <T extends RelacionamentoException> void validarAutoRelacionamento(Usuario origem, Usuario alvo, T excecao)
            throws T {
        if (origem.getLogin().equals(alvo.getLogin())) {
            throw excecao;
        }
    }

    protected void validarBloqueioPorInimigo(Usuario origem, Usuario alvo)
            throws FuncaoInvalidaUsuarioInimigoException {
        if (alvo.temRelacionamento(Usuario.REL_INIMIGOS, origem.getLogin())) {
            throw new FuncaoInvalidaUsuarioInimigoException(alvo.getAtributo("nome"));
        }
    }
}
