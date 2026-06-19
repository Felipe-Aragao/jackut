package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.EsperandoAceitacaoDoConviteException;
import br.ufal.ic.jackut.exception.FuncaoInvalidaUsuarioInimigoException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoAmigoException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSeAutoAdicionarException;
import br.ufal.ic.jackut.model.Usuario;

public class AmizadeStrategy extends AbstractRelacionamentoStrategy {

    @Override
    public void adicionar(Usuario origem, Usuario alvo)
            throws FuncaoInvalidaUsuarioInimigoException, UsuarioNaoPodeSeAutoAdicionarException,
            UsuarioJaEstaAdicionadoComoAmigoException, EsperandoAceitacaoDoConviteException {
        if (origem.getLogin().equals(alvo.getLogin())) {
            throw new UsuarioNaoPodeSeAutoAdicionarException();
        }

        validarBloqueioPorInimigo(origem, alvo);

        if (origem.temRelacionamento(Usuario.REL_AMIGOS, alvo.getLogin())
                || alvo.temRelacionamento(Usuario.REL_AMIGOS, origem.getLogin())) {
            throw new UsuarioJaEstaAdicionadoComoAmigoException();
        }

        if (origem.temRelacionamento(Usuario.REL_CONVITES, alvo.getLogin())) {
            origem.adicionarRelacionamento(Usuario.REL_AMIGOS, alvo.getLogin());
            alvo.adicionarRelacionamento(Usuario.REL_AMIGOS, origem.getLogin());
            origem.removerRelacionamento(Usuario.REL_CONVITES, alvo.getLogin());
            return;
        }

        if (alvo.temRelacionamento(Usuario.REL_CONVITES, origem.getLogin())) {
            throw new EsperandoAceitacaoDoConviteException();
        }

        alvo.adicionarRelacionamento(Usuario.REL_CONVITES, origem.getLogin());
    }
}
