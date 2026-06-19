package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.FuncaoInvalidaUsuarioInimigoException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoPaqueraException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSerPaqueraDeSiMesmoException;
import br.ufal.ic.jackut.model.Usuario;

public class PaqueraStrategy extends AbstractRelacionamentoStrategy {

    @Override
    public void adicionar(Usuario origem, Usuario alvo)
            throws FuncaoInvalidaUsuarioInimigoException, UsuarioJaEstaAdicionadoComoPaqueraException,
            UsuarioNaoPodeSerPaqueraDeSiMesmoException {
        if (origem.getLogin().equals(alvo.getLogin())) {
            throw new UsuarioNaoPodeSerPaqueraDeSiMesmoException();
        }

        validarBloqueioPorInimigo(origem, alvo);

        if (origem.temRelacionamento(Usuario.REL_PAQUERAS, alvo.getLogin())) {
            throw new UsuarioJaEstaAdicionadoComoPaqueraException();
        }

        origem.adicionarRelacionamento(Usuario.REL_PAQUERAS, alvo.getLogin());
    }
}
