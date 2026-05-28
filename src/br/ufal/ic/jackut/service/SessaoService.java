package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.LoginOuSenhaInvalidoException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Sessao;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.SessaoRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;

/**
 * Serviço responsável pelo gerenciamento de sessões.
 */
public class SessaoService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;

    /**
     * Cria o serviço com o repositório de usuários e sessões fornecido.
     *
     * @param usuarioRepository repositório de usuários
     * @param sessaoRepository repositório de sessões
     */
    public SessaoService(UsuarioRepository usuarioRepository, SessaoRepository sessaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
    }

    /**
     * Valida as credenciais e abre uma sessão. Lança exceção se login/senha
     * forem inválidos ou não corresponderem a um usuário cadastrado.
     *
     * @param login login do usuário
     * @param senha senha do usuário
     * @return o id da sessão
     * @throws LoginOuSenhaInvalidoException se as credenciais forem inválidas
     */
    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException{

        if (login == null || senha == null || login.isEmpty() || senha.isEmpty()) {
            throw new LoginOuSenhaInvalidoException();
        }

        Usuario usuario = null;

        try {
            usuario = usuarioRepository.buscarUsuario(login);
            if (!usuario.getSenha().equals(senha)) {
                throw new LoginOuSenhaInvalidoException();
            }
        } catch (UsuarioNaoCadastradoException e){
            throw new LoginOuSenhaInvalidoException();
        }

        Sessao sessao = new Sessao(usuario);
        sessaoRepository.adicionarSessao(sessao);
        return sessao.getId();
    }

}
