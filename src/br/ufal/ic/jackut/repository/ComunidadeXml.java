package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.model.Comunidade;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsável pela persistência XML das comunidades.
 */
public class ComunidadeXml extends ArmazenamentoXml<Map<String, Comunidade>> {

    /**
     * Inicializa o armazenamento XML de comunidades.
     */
    public ComunidadeXml() {
        super("comunidades.xml", new HashMap<String, Comunidade>(), comunidades -> new HashMap<>(comunidades));
    }
}