package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Sessao;
import br.ufal.ic.jackut.model.Usuario;

import java.util.HashMap;
import java.util.Map;

/**
 * Repositório responsável por persistir e recuperar instâncias de `Sessao`.
 */

public class SessaoRepository {
    private final Map<String, Sessao> sessoes;

    public SessaoRepository() {
        this.sessoes = new HashMap<>();
    }

    /**
     * Adiciona uma sessão ao repositório.
     *
     * @param sessao a sessão a adicionar
     */
    public void adicionarSessao(Sessao sessao) {
        sessoes.put(sessao.getId(), sessao);
    }

    /**
     * Busca uma sessão pelo id.
     *
     * @param id id da sessão
     * @return a `Sessao` encontrada
     */
    public Sessao buscarSessao(String id) {
        return sessoes.get(id);
    }
}
