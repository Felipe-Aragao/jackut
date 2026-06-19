package br.ufal.ic.jackut.model;

import br.ufal.ic.jackut.exception.NaoHaMensagensException;
import br.ufal.ic.jackut.exception.NaoHaRecadosException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSeAutoEnviarMensagemException;

import java.util.*;

/**
 * Modelo que representa um usuário do sistema.
 * Contém `login`, `senha` e um mapa de atributos adicionais.
 */
public class Usuario {

    public static final String REL_AMIGOS = "amigos";
    public static final String REL_CONVITES = "convites";
    public static final String REL_IDOLOS = "idolos";
    public static final String REL_FAS = "fas";
    public static final String REL_PAQUERAS = "paqueras";
    public static final String REL_INIMIGOS = "inimigos";

    private String login;
    private String senha;

    private Map<String, String> atributos = new HashMap<>();
    private Map<String, Set<String>> relacionamentos = new HashMap<>();

    private List<Recado> recados = new ArrayList<>();
    private List<String> mensagens = new ArrayList<>();

    /**
     * Cria um usuário com login, senha e nome (armazenado em atributos).
     *
     * @param login login do usuário
     * @param senha senha do usuário
     * @param nome  nome do usuário
     */
    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;

        atributos.put("nome", nome);
    }

    /**
     * Construtor vazio necessário para serialização XML.
     */
    public Usuario(){}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna uma cópia do mapa de atributos do usuário.
     *
     * @return cópia do mapa de atributos
     */
    public Map<String, String> getAtributos() {
        return new HashMap<>(atributos);
    }

    /**
     * Retorna o valor de um atributo do perfil.
     *
     * @param atributo nome do atributo buscado
     * @return valor do atributo, ou null se não estiver preenchido
     */
    public String getAtributo(String atributo) {
        return atributos.get(atributo);
    }

    /**
     * Cria ou altera um atributo do perfil do usuário.
     *
     * @param atributo nome do atributo
     * @param valor novo valor do atributo
     */
    public void editarAtributo(String atributo, String valor) {
        atributos.put(atributo, valor);
    }

    /**
     * Substitui o mapa de atributos do usuário.
     *
     * @param atributos novo mapa de atributos
     */
    public void setAtributos(Map<String, String> atributos) {
        this.atributos = new HashMap<>(atributos);
    }

    /**
     * Retorna uma cópia do mapa de relacionamentos do usuário.
     *
     * @return mapa de relacionamentos
     */
    public Map<String, Set<String>> getRelacionamentos() {
        Map<String, Set<String>> copia = new HashMap<>();

        for (Map.Entry<String, Set<String>> entrada : getMapaRelacionamentos().entrySet()) {
            copia.put(entrada.getKey(), new LinkedHashSet<>(entrada.getValue()));
        }

        return copia;
    }

    /**
     * Substitui o mapa de relacionamentos do usuário.
     *
     * @param relacionamentos novo mapa de relacionamentos
     */
    public void setRelacionamentos(Map<String, Set<String>> relacionamentos) {
        this.relacionamentos = new HashMap<>();

        if (relacionamentos == null) {
            return;
        }

        for (Map.Entry<String, Set<String>> entrada : relacionamentos.entrySet()) {
            this.relacionamentos.put(entrada.getKey(), new LinkedHashSet<>(entrada.getValue()));
        }
    }

    /**
     * Retorna uma cópia da coleção de amigos.
     *
     * @return cópia dos amigos
     */
    public Set<String> getAmigos() {
        return getRelacionamentos(REL_AMIGOS);
    }

    /**
     * Substitui a coleção de amigos.
     *
     * @param amigos nova coleção de amigos
     */
    public void setAmigos(Set<String> amigos) {
        setRelacionamentos(REL_AMIGOS, amigos);
    }

    /**
     * Retorna uma cópia da coleção de um tipo de relacionamento.
     *
     * @param tipo tipo de relacionamento
     * @return relacionamentos do tipo informado
     */
    public Set<String> getRelacionamentos(String tipo) {
        return new LinkedHashSet<>(colecaoRelacionamentos(tipo));
    }

    /**
     * Substitui a coleção de um tipo de relacionamento.
     *
     * @param tipo tipo de relacionamento
     * @param valores novos valores
     */
    public void setRelacionamentos(String tipo, Set<String> valores) {
        getMapaRelacionamentos().put(tipo, new LinkedHashSet<>(valores));
    }

    /**
     * Adiciona um login ao tipo de relacionamento informado.
     *
     * @param tipo tipo de relacionamento
     * @param login login relacionado
     */
    public void adicionarRelacionamento(String tipo, String login) {
        colecaoRelacionamentos(tipo).add(login);
    }

    /**
     * Verifica se um login pertence ao tipo de relacionamento informado.
     *
     * @param tipo tipo de relacionamento
     * @param login login a ser consultado
     * @return true se o relacionamento existir
     */
    public boolean temRelacionamento(String tipo, String login) {
        return colecaoRelacionamentos(tipo).contains(login);
    }

    /**
     * Remove um login do tipo de relacionamento informado.
     *
     * @param tipo tipo de relacionamento
     * @param login login a remover
     */
    public void removerRelacionamento(String tipo, String login) {
        colecaoRelacionamentos(tipo).remove(login);
    }

    /**
     * Lista um tipo de relacionamento no formato esperado pela fachada.
     *
     * @param tipo tipo de relacionamento
     * @return relacionamentos no formato {login1,login2}
     */
    public String listarRelacionamentos(String tipo) {
        return "{" + String.join(",", colecaoRelacionamentos(tipo)) + "}";
    }

    /**
     * Verifica se o login informado está na lista de amigos do usuário.
     *
     * @param login login a ser verificado
     * @return true se o login for amigo, false caso contrário
     */
    public boolean ehAmigoDe(String login) {
        return temRelacionamento(REL_AMIGOS, login);
    }

    /**
     * Retorna os amigos no formato esperado pela fachada.
     *
     * @return amigos no formato {login1,login2}
     */
    public String listarAmigos() {
        return listarRelacionamentos(REL_AMIGOS);
    }

    /**
     * Retorna uma cópia dos convites recebidos.
     *
     * @return cópia dos convites
     */
    public Set<String> getConvites() {
        return getRelacionamentos(REL_CONVITES);
    }

    /**
     * Substitui a coleção de convites.
     *
     * @param convites nova coleção de convites
     */
    public void setConvites(Set<String> convites) {
        setRelacionamentos(REL_CONVITES, convites);
    }

    /**
     * Retorna uma cópia da lista de recados do usuário.
     * @return lista de recados (cópia)
     */
    public List<Recado> getRecados() {
        return new ArrayList<>(recados);
    }

    /**
     * Substitui a lista de recados do usuário.
     * @param recados nova lista de recados
     */
    public void setRecados(List<Recado> recados) {
        this.recados = new ArrayList<>(recados);
    }

    /**
     * Envia um recado para outro usuário.
     *
     * @param destinatario usuário que receberá o recado
     * @param mensagem texto do recado
     * @throws UsuarioNaoPodeSeAutoEnviarMensagemException se o destinatário for o próprio usuário
     */
    public void enviarRecadoPara(Usuario destinatario, String mensagem)
            throws UsuarioNaoPodeSeAutoEnviarMensagemException {
        if (this.login.equals(destinatario.login)) {
            throw new UsuarioNaoPodeSeAutoEnviarMensagemException();
        }

        destinatario.receberRecado(new Recado(this.login, destinatario.login, mensagem));
    }

    /**
     * Retorna e remove o recado mais antigo recebido pelo usuário.
     *
     * @return texto do recado mais antigo
     * @throws NaoHaRecadosException se não existirem recados
     */
    public String lerRecadoMaisAntigo() throws NaoHaRecadosException {
        if (recados.isEmpty()) {
            throw new NaoHaRecadosException();
        }

        return recados.remove(0).getMensagem();
    }

    public List<String> getMensagens() {
        return new ArrayList<>(mensagens);
    }

    public void setMensagens(List<String> mensagens) {
        this.mensagens = new ArrayList<>(mensagens);
    }

    public void receberMensagem(String mensagem) {
        mensagens.add(mensagem);
    }

    public String lerMensagemMaisAntiga() throws NaoHaMensagensException {
        if (mensagens.isEmpty()) {
            throw new NaoHaMensagensException();
        }

        return mensagens.remove(0);
    }

    public void receberRecadoDoSistema(String mensagem) {
        receberRecado(new Recado("Jackut", login, mensagem));
    }

    private Map<String, Set<String>> getMapaRelacionamentos() {
        if (relacionamentos == null) {
            relacionamentos = new HashMap<>();
        }

        return relacionamentos;
    }

    private Set<String> colecaoRelacionamentos(String tipo) {
        Map<String, Set<String>> mapa = getMapaRelacionamentos();
        Set<String> valores = mapa.get(tipo);

        if (valores == null) {
            valores = new LinkedHashSet<>();
            mapa.put(tipo, valores);
        }

        return valores;
    }

    private void receberRecado(Recado recado) {
        recados.add(recado);
    }
}
