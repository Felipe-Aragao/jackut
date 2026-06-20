package br.ufal.ic.jackut.service.observer;

import br.ufal.ic.jackut.model.Usuario;

/**
 * Observador acionado quando uma paquera é adicionada.
 */
public interface PaqueraObserver {

    /**
     * Notifica a adição de uma paquera entre origem e alvo.
     *
     * @param origem usuário que adicionou a paquera
     * @param alvo usuário marcado como paquera
     */
    void paqueraAdicionada(Usuario origem, Usuario alvo);
}
