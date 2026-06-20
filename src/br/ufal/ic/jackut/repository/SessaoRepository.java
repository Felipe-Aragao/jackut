package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.model.Sessao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositório responsável por manter e recuperar instâncias de `Sessao` em memória.
 */

public class SessaoRepository {
    private final Map<String, Sessao> sessoes;

    /**
     * Inicializa o repositório de sessões vazio.
     */
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

    public void removerSessoesDoUsuario(String login) {
        List<String> idsRemovidos = new ArrayList<>();

        for (Map.Entry<String, Sessao> entrada : sessoes.entrySet()) {
            Sessao sessao = entrada.getValue();
            if (sessao.getUsuario() != null && login.equals(sessao.getUsuario().getLogin())) {
                idsRemovidos.add(entrada.getKey());
            }
        }

        for (String id : idsRemovidos) {
            sessoes.remove(id);
        }
    }

    /**
     * Remove todas as sessões mantidas em memória.
     */
    public void limpar() {
        sessoes.clear();
    }
}
