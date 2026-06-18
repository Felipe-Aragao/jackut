package br.ufal.ic.jackut.model;

import br.ufal.ic.jackut.exception.EsperandoAceitacaoDoConviteException;
import br.ufal.ic.jackut.exception.NaoHaRecadosException;
import br.ufal.ic.jackut.exception.NaoHaMensagensException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoAmigoException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSeAutoAdicionarException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSeAutoEnviarMensagemException;

import java.util.*;

/**
 * Modelo que representa um usuário do sistema.
 * Contém `login`, `senha` e um mapa de atributos adicionais.
 */
public class Usuario {

    private String login;
    private String senha;

    private Map<String, String> atributos = new HashMap<>();

    private Set<String> amigos = new LinkedHashSet<>();
    private Set<String> convites = new LinkedHashSet<>();
    
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
     * Retorna uma cópia da coleção de amigos.
     *
     * @return cópia dos amigos
     */
    public Set<String> getAmigos() {
        return new LinkedHashSet<>(amigos);
    }

    /**
     * Substitui a coleção de amigos.
     *
     * @param amigos nova coleção de amigos
     */
    public void setAmigos(Set<String> amigos) {
        this.amigos = new LinkedHashSet<>(amigos);
    }

    /**
     * Executa a regra de envio e aceitação de convites de amizade.
     *
     * @param usuarioAlvo usuário que receberá o convite ou será aceito como amigo
     * @throws UsuarioNaoPodeSeAutoAdicionarException se o usuário tentar adicionar a si mesmo
     * @throws UsuarioJaEstaAdicionadoComoAmigoException se os usuários já forem amigos
     * @throws EsperandoAceitacaoDoConviteException se já houver convite pendente para o usuário alvo
     */
    public void adicionarAmigo(Usuario usuarioAlvo)
            throws UsuarioNaoPodeSeAutoAdicionarException, UsuarioJaEstaAdicionadoComoAmigoException,
            EsperandoAceitacaoDoConviteException {
        if (this.login.equals(usuarioAlvo.login)) {
            throw new UsuarioNaoPodeSeAutoAdicionarException();
        }

        if (this.amigos.contains(usuarioAlvo.login) || usuarioAlvo.amigos.contains(this.login)) {
            throw new UsuarioJaEstaAdicionadoComoAmigoException();
        }

        if (this.convites.contains(usuarioAlvo.login)) {
            this.amigos.add(usuarioAlvo.login);
            usuarioAlvo.amigos.add(this.login);
            this.convites.remove(usuarioAlvo.login);
            return;
        }

        if (usuarioAlvo.convites.contains(this.login)) {
            throw new EsperandoAceitacaoDoConviteException();
        }

        usuarioAlvo.convites.add(this.login);
    }

    /**
     * Verifica se o login informado está na lista de amigos do usuário.
     *
     * @param login login a ser verificado
     * @return true se o login for amigo, false caso contrário
     */
    public boolean ehAmigoDe(String login) {
        return amigos.contains(login);
    }

    /**
     * Retorna os amigos no formato esperado pela fachada.
     *
     * @return amigos no formato {login1,login2}
     */
    public String listarAmigos() {
        return "{" + String.join(",", amigos) + "}";
    }

    /**
     * Retorna uma cópia dos convites recebidos.
     *
     * @return cópia dos convites
     */
    public Set<String> getConvites() {
        return new LinkedHashSet<>(convites);
    }

    /**
     * Substitui a coleção de convites.
     *
     * @param convites nova coleção de convites
     */
    public void setConvites(Set<String> convites) {
        this.convites = new LinkedHashSet<>(convites);
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

    private void receberRecado(Recado recado) {
        recados.add(recado);
    }
}