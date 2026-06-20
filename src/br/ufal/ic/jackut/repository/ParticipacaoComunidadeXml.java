package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.model.ParticipacaoComunidade;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsável pela persistência XML das participações em comunidades.
 */
public class ParticipacaoComunidadeXml extends ArmazenamentoXml<List<ParticipacaoComunidade>> {

    /**
     * Inicializa o armazenamento XML de participações em comunidades.
     */
    public ParticipacaoComunidadeXml() {
        super("participacoes-comunidades.xml", new ArrayList<ParticipacaoComunidade>(),
                participacoes -> new ArrayList<>(participacoes));
    }
}