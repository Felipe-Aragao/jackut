package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.LoginOuSenhaInvalidoException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.repository.UsuarioRepository;

/**
 * Serviço responsável pelo gerenciamento de sessões.
 */
public class SessaoService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Cria o serviço com o repositório de usuários fornecido.
     *
     * @param usuarioRepository repositório de usuários
     */
    public SessaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Valida as credenciais e abre uma sessão. Lança exceção se login/senha
     * forem inválidos ou não corresponderem a um usuário cadastrado.
     *
     * @param login login do usuário
     * @param senha senha do usuário
     * @throws LoginOuSenhaInvalidoException se as credenciais forem inválidas
     */
    public void abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException{

        if (login == null || senha == null || login.isEmpty() || senha.isEmpty()) {
            throw new LoginOuSenhaInvalidoException();
        }

        try {
            if (!usuarioRepository.buscarUsuario(login).getSenha().equals(senha)) {
                throw new LoginOuSenhaInvalidoException();
            }
        } catch (UsuarioNaoCadastradoException e){
            throw new LoginOuSenhaInvalidoException();
        }
    }

}
