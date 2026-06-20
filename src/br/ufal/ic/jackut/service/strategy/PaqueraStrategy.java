package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.FuncaoInvalidaUsuarioInimigoException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoPaqueraException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSerPaqueraDeSiMesmoException;
import br.ufal.ic.jackut.model.Usuario;

/**
 * Estratégia que aplica as regras de paquera entre usuários.
 */
public class PaqueraStrategy extends AbstractRelacionamentoStrategy {

    /**
     * Inicializa a estratégia de paquera.
     */
    public PaqueraStrategy() {}
    /**
     * Adiciona o alvo como paquera da origem.
     *
     * @param origem usuário que adiciona a paquera
     * @param alvo usuário marcado como paquera
     * @throws FuncaoInvalidaUsuarioInimigoException se o alvo marcou a origem como inimiga
     * @throws UsuarioJaEstaAdicionadoComoPaqueraException se a paquera já estiver adicionada
     * @throws UsuarioNaoPodeSerPaqueraDeSiMesmoException se origem e alvo forem o mesmo usuário
     */
    @Override
    public void adicionar(Usuario origem, Usuario alvo)
            throws FuncaoInvalidaUsuarioInimigoException, UsuarioJaEstaAdicionadoComoPaqueraException,
            UsuarioNaoPodeSerPaqueraDeSiMesmoException {
        validarAutoRelacionamento(origem, alvo, new UsuarioNaoPodeSerPaqueraDeSiMesmoException());

        validarBloqueioPorInimigo(origem, alvo);

        if (origem.temRelacionamento(Usuario.REL_PAQUERAS, alvo.getLogin())) {
            throw new UsuarioJaEstaAdicionadoComoPaqueraException();
        }

        origem.adicionarRelacionamento(Usuario.REL_PAQUERAS, alvo.getLogin());
    }
}
