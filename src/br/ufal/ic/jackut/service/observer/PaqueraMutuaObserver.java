package br.ufal.ic.jackut.service.observer;

import br.ufal.ic.jackut.model.Usuario;

public class PaqueraMutuaObserver implements PaqueraObserver {

    @Override
    public void paqueraAdicionada(Usuario origem, Usuario alvo) {
        if (!alvo.temRelacionamento(Usuario.REL_PAQUERAS, origem.getLogin())) {
            return;
        }

        origem.receberRecadoDoSistema(alvo.getAtributo("nome") + " é seu paquera - Recado do Jackut.");
        alvo.receberRecadoDoSistema(origem.getAtributo("nome") + " é seu paquera - Recado do Jackut.");
    }
}
