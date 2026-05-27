package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.LoginOuSenhaInvalidoException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.UsuarioRepository;

public class SessaoService {

    private final UsuarioRepository usuarioRepository;

    public SessaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException{

        if (login == null || senha == null || login.isEmpty() || senha.isEmpty()) {
            throw new LoginOuSenhaInvalidoException();
        }

        try {
            usuarioRepository.buscarUsuario(login);
        } catch (UsuarioNaoCadastradoException e){
            throw new LoginOuSenhaInvalidoException();
        }

        if (!(usuarioRepository.getUsuarios().get(login).getSenha().equals(senha))) {
            throw new LoginOuSenhaInvalidoException();
        }

    }

}
