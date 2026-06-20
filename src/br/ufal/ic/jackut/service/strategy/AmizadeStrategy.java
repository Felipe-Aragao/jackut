package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.EsperandoAceitacaoDoConviteException;
import br.ufal.ic.jackut.exception.FuncaoInvalidaUsuarioInimigoException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoAmigoException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSeAutoAdicionarException;
import br.ufal.ic.jackut.model.Usuario;

/**
 * Estratégia que aplica as regras de amizade e convites entre usuários.
 */
public class AmizadeStrategy extends AbstractRelacionamentoStrategy {

    /**
     * Inicializa a estratégia de amizade.
     */
    public AmizadeStrategy() {}
    /**
     * Adiciona uma amizade ou cria um convite pendente entre dois usuários.
     *
     * @param origem usuário que inicia o pedido de amizade
     * @param alvo usuário alvo do pedido de amizade
     * @throws FuncaoInvalidaUsuarioInimigoException se o alvo marcou a origem como inimiga
     * @throws UsuarioNaoPodeSeAutoAdicionarException se origem e alvo forem o mesmo usuário
     * @throws UsuarioJaEstaAdicionadoComoAmigoException se a amizade já existir
     * @throws EsperandoAceitacaoDoConviteException se já houver convite pendente para a origem
     */
    @Override
    public void adicionar(Usuario origem, Usuario alvo)
            throws FuncaoInvalidaUsuarioInimigoException, UsuarioNaoPodeSeAutoAdicionarException,
            UsuarioJaEstaAdicionadoComoAmigoException, EsperandoAceitacaoDoConviteException {
        validarAutoRelacionamento(origem, alvo, new UsuarioNaoPodeSeAutoAdicionarException());

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
