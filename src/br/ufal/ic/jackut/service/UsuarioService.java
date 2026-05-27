package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.ContaJaExisteException;
import br.ufal.ic.jackut.exception.LoginInvalidoException;
import br.ufal.ic.jackut.exception.SenhaInvalidaException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.UsuarioRepository;

/**
 * Serviço para operações relacionadas a usuários: criação e leitura de atributos.
 */
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Cria o serviço com o repositório de usuários fornecido.
     *
     * @param usuarioRepository repositório de usuários
     */
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException {
        Usuario usuario = usuarioRepository.buscarUsuario(login);

        String valorAtributo = usuario.getAtributos().get(atributo);

        return valorAtributo;
    }
}
