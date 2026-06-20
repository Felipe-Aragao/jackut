package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.exception.ComunidadeNaoExisteException;
import br.ufal.ic.jackut.model.Comunidade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComunidadeRepository {

    private final Map<String, Comunidade> comunidades;
    private final ComunidadeXml armazenamento;

    public ComunidadeRepository() {
        this.armazenamento = new ComunidadeXml();
        this.comunidades = armazenamento.carregar();
    }

    public void save() {
        armazenamento.salvar(comunidades);
    }

    public void adicionarComunidade(Comunidade comunidade) {
        comunidades.put(comunidade.getNome(), comunidade);
    }

    public boolean existeComunidade(String nome) {
        return comunidades.containsKey(nome);
    }

    public void removerComunidade(String nome) {
        comunidades.remove(nome);
    }

    public List<String> listarComunidadesDoDono(String loginDono) {
        List<String> nomes = new ArrayList<>();

        for (Comunidade comunidade : comunidades.values()) {
            if (loginDono.equals(comunidade.getDono())) {
                nomes.add(comunidade.getNome());
            }
        }

        return nomes;
    }

    public void limpar() {
        comunidades.clear();
    }

    public void apagarPersistencia() {
        armazenamento.apagar();
    }

    public Comunidade buscarComunidade(String nome) throws ComunidadeNaoExisteException {

        Comunidade comunidade = comunidades.get(nome);

        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }

        return comunidade;
    }

    public Map<String, Comunidade> getComunidades() {
        return new HashMap<>(comunidades);
    }
}
