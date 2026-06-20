package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoInimigoException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSerInimigoDeSiMesmoException;
import br.ufal.ic.jackut.model.Usuario;

public class InimigoStrategy extends AbstractRelacionamentoStrategy {

    @Override
    public void adicionar(Usuario origem, Usuario alvo)
            throws UsuarioJaEstaAdicionadoComoInimigoException, UsuarioNaoPodeSerInimigoDeSiMesmoException {
        validarAutoRelacionamento(origem, alvo, new UsuarioNaoPodeSerInimigoDeSiMesmoException());

        if (origem.temRelacionamento(Usuario.REL_INIMIGOS, alvo.getLogin())) {
            throw new UsuarioJaEstaAdicionadoComoInimigoException();
        }

        origem.adicionarRelacionamento(Usuario.REL_INIMIGOS, alvo.getLogin());
    }
}
