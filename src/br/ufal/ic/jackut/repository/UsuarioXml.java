package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.model.Usuario;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsável pela persistência XML dos usuários.
 */
public class UsuarioXml extends ArmazenamentoXml<Map<String, Usuario>> {

    /**
     * Inicializa o armazenamento XML de usuários.
     */
    public UsuarioXml() {
        super("usuario.xml", new HashMap<String, Usuario>(), usuarios -> new HashMap<>(usuarios));
    }
}