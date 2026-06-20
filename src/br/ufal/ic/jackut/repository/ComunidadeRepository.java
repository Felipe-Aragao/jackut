package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.exception.ComunidadeNaoExisteException;
import br.ufal.ic.jackut.model.Comunidade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositório responsável por manter e recuperar comunidades.
 */
public class ComunidadeRepository {

    private final Map<String, Comunidade> comunidades;
    private final ComunidadeXml armazenamento;

    /**
     * Inicializa o repositório carregando as comunidades persistidas, se houver.
     */
    public ComunidadeRepository() {
        this.armazenamento = new ComunidadeXml();
        this.comunidades = armazenamento.carregar();
    }

    /**
     * Persiste o mapa de comunidades atual em disco (XML).
     */
    public void save() {
        armazenamento.salvar(comunidades);
    }

    /**
     * Adiciona uma comunidade ao repositório.
     *
     * @param comunidade comunidade a adicionar
     */
    public void adicionarComunidade(Comunidade comunidade) {
        comunidades.put(comunidade.getNome(), comunidade);
    }

    /**
     * Verifica se existe uma comunidade com o nome informado.
     *
     * @param nome nome da comunidade
     * @return true se a comunidade existir
     */
    public boolean existeComunidade(String nome) {
        return comunidades.containsKey(nome);
    }

    /**
     * Remove a comunidade com o nome informado.
     *
     * @param nome nome da comunidade a remover
     */
    public void removerComunidade(String nome) {
        comunidades.remove(nome);
    }

    /**
     * Lista os nomes das comunidades criadas pelo dono informado.
     *
     * @param loginDono login do dono das comunidades
     * @return lista de nomes das comunidades do dono
     */
    public List<String> listarComunidadesDoDono(String loginDono) {
        List<String> nomes = new ArrayList<>();

        for (Comunidade comunidade : comunidades.values()) {
            if (loginDono.equals(comunidade.getDono())) {
                nomes.add(comunidade.getNome());
            }
        }

        return nomes;
    }

    /**
     * Remove todas as comunidades mantidas em memória.
     */
    public void limpar() {
        comunidades.clear();
    }

    /**
     * Remove os dados persistidos em disco.
     */
    public void apagarPersistencia() {
        armazenamento.apagar();
    }

    /**
     * Busca uma comunidade pelo nome.
     *
     * @param nome nome da comunidade
     * @return comunidade encontrada
     * @throws ComunidadeNaoExisteException se não existir comunidade com o nome
     */
    public Comunidade buscarComunidade(String nome) throws ComunidadeNaoExisteException {

        Comunidade comunidade = comunidades.get(nome);

        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }

        return comunidade;
    }

    /**
     * Retorna uma cópia do mapa de comunidades.
     *
     * @return mapa de nome para comunidade
     */
    public Map<String, Comunidade> getComunidades() {
        return new HashMap<>(comunidades);
    }
}
