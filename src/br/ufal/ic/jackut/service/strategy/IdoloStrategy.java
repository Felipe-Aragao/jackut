package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.FuncaoInvalidaUsuarioInimigoException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoIdoloException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSerFaDeSiMesmoException;
import br.ufal.ic.jackut.model.Usuario;

public class IdoloStrategy extends AbstractRelacionamentoStrategy {

    @Override
    public void adicionar(Usuario origem, Usuario alvo)
            throws FuncaoInvalidaUsuarioInimigoException, UsuarioJaEstaAdicionadoComoIdoloException,
            UsuarioNaoPodeSerFaDeSiMesmoException {
        if (origem.getLogin().equals(alvo.getLogin())) {
            throw new UsuarioNaoPodeSerFaDeSiMesmoException();
        }

        validarBloqueioPorInimigo(origem, alvo);

        if (origem.temRelacionamento(Usuario.REL_IDOLOS, alvo.getLogin())) {
            throw new UsuarioJaEstaAdicionadoComoIdoloException();
        }

        origem.adicionarRelacionamento(Usuario.REL_IDOLOS, alvo.getLogin());
        alvo.adicionarRelacionamento(Usuario.REL_FAS, origem.getLogin());
    }
}
