package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.*;
import br.ufal.ic.jackut.model.Sessao;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.SessaoRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;

import java.util.Map;

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

}
