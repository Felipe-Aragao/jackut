package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.RelacionamentoException;
import br.ufal.ic.jackut.model.Usuario;

/**
 * Estratégia para aplicar regras de adição de relacionamento entre usuários.
 */
public interface RelacionamentoStrategy {

    /**
     * Adiciona o relacionamento entre o usuário de origem e o usuário alvo.
     *
     * @param origem usuário que inicia o relacionamento
     * @param alvo usuário alvo do relacionamento
     * @throws RelacionamentoException se alguma regra do relacionamento for violada
     */
    void adicionar(Usuario origem, Usuario alvo) throws RelacionamentoException;
}
