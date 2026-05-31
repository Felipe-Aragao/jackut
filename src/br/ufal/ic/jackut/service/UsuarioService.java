package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.*;
import br.ufal.ic.jackut.model.Sessao;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.SessaoRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;

import java.util.*;

/**
 * Serviço para operações relacionadas a usuários: criação e leitura de atributos.
 */
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;

    /**
     * Cria o serviço com o repositório de usuários e sessões fornecido.
     *
     * @param usuarioRepository repositório de usuários
     * @param sessaoRepository repositório de sessões
     */
    public UsuarioService(UsuarioRepository usuarioRepository, SessaoRepository sessaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
    }

    /**
     * Cria um usuário após validar login e senha.
     *
     * @param login login desejado
     * @param senha senha do usuário
     * @param nome  nome do usuário
     * @throws ContaJaExisteException se já existir usuário com o mesmo login
     * @throws LoginInvalidoException se o login for nulo ou vazio
     * @throws SenhaInvalidaException se a senha for nula ou vazia
     */
    public void criarUsuario(String login, String senha, String nome)
            throws ContaJaExisteException, LoginInvalidoException, SenhaInvalidaException {

        if (login == null || login.isEmpty()) {
            throw new LoginInvalidoException();
        }

        if (senha == null || senha.isEmpty()) {
            throw new SenhaInvalidaException();
        }

        try {
            if (usuarioRepository.buscarUsuario(login)!= null) {
                throw new ContaJaExisteException();
            }
        } catch (UsuarioNaoCadastradoException ignore) {}

        Usuario usuario = new Usuario(login, senha, nome);
        usuarioRepository.adicionarUsuario(usuario);
    }

    /**
     * Retorna o valor de um atributo do usuário com o login informado.
     *
     * @param login    o login do usuário
     * @param atributo o nome do atributo a obter
     * @return o valor do atributo ou null se inexistente
     * @throws UsuarioNaoCadastradoException se o usuário não existir
     */
    public String getAtributoUsuario(String login, String atributo)
            throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        Usuario usuario = usuarioRepository.buscarUsuario(login);

        String valorAtributo = usuario.getAtributos().get(atributo);

        if (valorAtributo == null) {
            throw new AtributoNaoPreenchidoException();
        }

        return valorAtributo;
    }

    /**
     * Edita o valor de um atributo do usuário com base no id da sessão.
     * @param id id da sessão
     * @param atributo atributo que será editado
     * @param valor novo valor do atributo
     * @throws UsuarioNaoCadastradoException se não existir sessão
     */

    public void editarPerfil(String id, String atributo, String valor)
            throws UsuarioNaoCadastradoException{

        Sessao sessao = sessaoRepository.buscarSessao(id);

        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuario = sessao.getUsuario();

        Map<String, String> atributos = usuario.getAtributos();

        if (atributos.replace(atributo, valor) == null) {
            atributos.put(atributo, valor);
        }

        usuario.setAtributos(atributos);
    }

    /**
     * Envia um pedido de amizade do usuário identificado pela sessão `id`
     * ao usuário `amigo`. O relacionamento só é efetivado quando o outro
     * usuário adicionar de volta.
     *
     * @param id id da sessão do usuário que envia o pedido
     * @param amigo login do usuário alvo do pedido
     * @throws UsuarioNaoCadastradoException se a sessão for inválida
     * @throws UsuarioJaEstaAdicionadoComoAmigoException se já são amigos
     * @throws EsperandoAceitacaoDoConviteException se já existe um convite pendente
     * @throws UsuarioNaoPodeSiAutoAdicionarException se o usuário tentar adicionar a si mesmo
     */
    public void adicionarAmigo(String id, String amigo) throws UsuarioNaoCadastradoException, UsuarioJaEstaAdicionadoComoAmigoException, EsperandoAceitacaoDoConviteException, UsuarioNaoPodeSiAutoAdicionarException {

        Sessao sessao = sessaoRepository.buscarSessao(id);

        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuario = sessao.getUsuario();
        if (usuario.getLogin().equals(amigo)) {
            throw new UsuarioNaoPodeSiAutoAdicionarException();
        }

        Usuario usuarioAlvo = usuarioRepository.buscarUsuario(amigo);

        Set<String> amigos = usuario.getAmigos();
        Set<String> convites = usuario.getConvites();

        Set<String> amigosAlvo = usuarioAlvo.getAmigos();
        Set<String> convitesAlvo = usuarioAlvo.getConvites();

        if (amigos.contains(amigo) || amigosAlvo.contains(usuario.getLogin())) {
            throw new UsuarioJaEstaAdicionadoComoAmigoException();
        }

        if (convites.contains(amigo)) {
            amigos.add(amigo);
            amigosAlvo.add(usuario.getLogin());
            convites.remove(amigo);
        } else {
            if (convitesAlvo.contains(usuario.getLogin())) {
                throw new EsperandoAceitacaoDoConviteException();
            }
            convitesAlvo.add(usuario.getLogin());
        }

        usuario.setAmigos(amigos);
        usuario.setConvites(convites);
        usuarioAlvo.setAmigos(amigosAlvo);
        usuarioAlvo.setConvites(convitesAlvo);
    }
    /**
     * Verifica se `amigo` faz parte da lista de amigos de `login`.
     *
     * @param login login do usuário que consulta
     * @param amigo login do possível amigo
     * @return true se são amigos, false caso contrário
     * @throws UsuarioNaoCadastradoException se o usuário do `login` não existir
     */
    public boolean ehAmigo(String login ,String amigo) throws UsuarioNaoCadastradoException {

        Usuario usuario = usuarioRepository.buscarUsuario(login);

        Set<String> amigos = usuario.getAmigos();

        return amigos.contains(amigo);
    }

    /**
     * Retorna a lista de amigos do usuário no formato {a,b,c}
     *
     * @param login login do usuário
     * @return string contendo os amigos do usuário
     * @throws UsuarioNaoCadastradoException se o usuário não existir
     */
    public String getAmigos(String login) throws UsuarioNaoCadastradoException {
        Usuario usuario = usuarioRepository.buscarUsuario(login);

        Set<String> amigos = usuario.getAmigos();

        return "{" + String.join(",", amigos) + "}";
    }
}
