package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.model.ParticipacaoComunidade;

import java.util.ArrayList;
import java.util.List;

public class ParticipacaoComunidadeRepository {

    private final List<ParticipacaoComunidade> participacoes;
    private final ParticipacaoComunidadeXml armazenamento;

    public ParticipacaoComunidadeRepository() {
        this.armazenamento = new ParticipacaoComunidadeXml();
        this.participacoes = armazenamento.carregar();
    }

    public void save() {
        armazenamento.salvar(participacoes);
    }

    public void adicionar(String login, String comunidade) {
        participacoes.add(new ParticipacaoComunidade(login, comunidade));
    }

    public boolean existe(String login, String comunidade) {
        for (ParticipacaoComunidade participacao : participacoes) {
            if (participacao.getLogin().equals(login) && participacao.getComunidade().equals(comunidade)) {
                return true;
            }
        }
        return false;
    }

    public List<String> listarComunidades(String login) {
        List<String> comunidades = new ArrayList<>();
        for (ParticipacaoComunidade participacao : participacoes) {
            if (participacao.getLogin().equals(login)) {
                comunidades.add(participacao.getComunidade());
            }
        }
        return comunidades;
    }

    public List<String> listarMembros(String comunidade) {
        List<String> membros = new ArrayList<>();
        for (ParticipacaoComunidade participacao : participacoes) {
            if (participacao.getComunidade().equals(comunidade)) {
                membros.add(participacao.getLogin());
            }
        }
        return membros;
    }

    public void limpar() {
        participacoes.clear();
    }

    public void apagarPersistencia() {
        armazenamento.apagar();
    }
}