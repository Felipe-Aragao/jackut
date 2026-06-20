package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.model.ParticipacaoComunidade;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Repositório responsável por manter as participações de usuários em comunidades.
 */
public class ParticipacaoComunidadeRepository {

    private final List<ParticipacaoComunidade> participacoes;
    private final ParticipacaoComunidadeXml armazenamento;

    /**
     * Inicializa o repositório carregando as participações persistidas, se houver.
     */
    public ParticipacaoComunidadeRepository() {
        this.armazenamento = new ParticipacaoComunidadeXml();
        this.participacoes = armazenamento.carregar();
    }

    /**
     * Persiste a lista de participações atual em disco (XML).
     */
    public void save() {
        armazenamento.salvar(participacoes);
    }

    /**
     * Adiciona a participação de um usuário em uma comunidade.
     *
     * @param login login do usuário participante
     * @param comunidade nome da comunidade
     */
    public void adicionar(String login, String comunidade) {
        participacoes.add(new ParticipacaoComunidade(login, comunidade));
    }

    /**
     * Verifica se a participação informada já existe.
     *
     * @param login login do usuário participante
     * @param comunidade nome da comunidade
     * @return true se a participação existir
     */
    public boolean existe(String login, String comunidade) {
        for (ParticipacaoComunidade participacao : participacoes) {
            if (participacao.getLogin().equals(login) && participacao.getComunidade().equals(comunidade)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Lista as comunidades das quais o usuário participa.
     *
     * @param login login do usuário
     * @return lista de nomes das comunidades
     */
    public List<String> listarComunidades(String login) {
        List<String> comunidades = new ArrayList<>();
        for (ParticipacaoComunidade participacao : participacoes) {
            if (participacao.getLogin().equals(login)) {
                comunidades.add(participacao.getComunidade());
            }
        }
        return comunidades;
    }

    /**
     * Lista os membros de uma comunidade.
     *
     * @param comunidade nome da comunidade
     * @return lista de logins dos membros
     */
    public List<String> listarMembros(String comunidade) {
        List<String> membros = new ArrayList<>();
        for (ParticipacaoComunidade participacao : participacoes) {
            if (participacao.getComunidade().equals(comunidade)) {
                membros.add(participacao.getLogin());
            }
        }
        return membros;
    }

    /**
     * Remove todas as participações do usuário informado.
     *
     * @param login login do usuário
     */
    public void removerPorLogin(String login) {
        List<ParticipacaoComunidade> participacoesRemovidas = new ArrayList<>();

        for (ParticipacaoComunidade participacao : participacoes) {
            if (login.equals(participacao.getLogin())) {
                participacoesRemovidas.add(participacao);
            }
        }

        participacoes.removeAll(participacoesRemovidas);
    }

    /**
     * Remove as participações associadas às comunidades informadas.
     *
     * @param comunidades nomes das comunidades removidas
     */
    public void removerPorComunidades(Set<String> comunidades) {
        List<ParticipacaoComunidade> participacoesRemovidas = new ArrayList<>();

        for (ParticipacaoComunidade participacao : participacoes) {
            if (comunidades.contains(participacao.getComunidade())) {
                participacoesRemovidas.add(participacao);
            }
        }

        participacoes.removeAll(participacoesRemovidas);
    }

    /**
     * Remove todas as participações mantidas em memória.
     */
    public void limpar() {
        participacoes.clear();
    }

    /**
     * Remove os dados persistidos em disco.
     */
    public void apagarPersistencia() {
        armazenamento.apagar();
    }
}
