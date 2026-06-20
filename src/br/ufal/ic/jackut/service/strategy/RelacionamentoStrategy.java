package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.RelacionamentoException;
import br.ufal.ic.jackut.model.Usuario;

public interface RelacionamentoStrategy {

    void adicionar(Usuario origem, Usuario alvo) throws RelacionamentoException;
}
