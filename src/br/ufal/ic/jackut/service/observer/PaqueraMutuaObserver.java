package br.ufal.ic.jackut.service.observer;

import br.ufal.ic.jackut.model.Usuario;

/**
 * Observador que envia recados do sistema quando uma paquera é mútua.
 */
public class PaqueraMutuaObserver implements PaqueraObserver {

    /**
     * Inicializa o observador de paquera mútua.
     */
    public PaqueraMutuaObserver() {}
    /**
     * Envia recados do sistema quando origem e alvo se paqueram mutuamente.
     *
     * @param origem usuário que adicionou a paquera
     * @param alvo usuário marcado como paquera
     */
    @Override
    public void paqueraAdicionada(Usuario origem, Usuario alvo) {
        if (!alvo.temRelacionamento(Usuario.REL_PAQUERAS, origem.getLogin())) {
            return;
        }

        origem.receberRecadoDoSistema(alvo.getAtributo("nome") + " é seu paquera - Recado do Jackut.");
        alvo.receberRecadoDoSistema(origem.getAtributo("nome") + " é seu paquera - Recado do Jackut.");
    }
}
