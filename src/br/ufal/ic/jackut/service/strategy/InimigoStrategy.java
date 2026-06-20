package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoInimigoException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSerInimigoDeSiMesmoException;
import br.ufal.ic.jackut.model.Usuario;

/**
 * Estratégia que aplica as regras de inimizade entre usuários.
 */
public class InimigoStrategy extends AbstractRelacionamentoStrategy {

    /**
     * Adiciona o alvo como inimigo da origem.
     *
     * @param origem usuário que adiciona o inimigo
     * @param alvo usuário marcado como inimigo
     * @throws UsuarioJaEstaAdicionadoComoInimigoException se o inimigo já estiver adicionado
     * @throws UsuarioNaoPodeSerInimigoDeSiMesmoException se origem e alvo forem o mesmo usuário
     */
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
