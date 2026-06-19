package br.ufal.ic.jackut.service.observer;

import br.ufal.ic.jackut.model.Usuario;

public interface PaqueraObserver {

    void paqueraAdicionada(Usuario origem, Usuario alvo);
}
