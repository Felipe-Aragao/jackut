package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.ContaJaExisteException;
import br.ufal.ic.jackut.exception.LoginInvalidoException;
import br.ufal.ic.jackut.exception.SenhaInvalidaException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.UsuarioRepository;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

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

    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException {
        Usuario usuario = usuarioRepository.buscarUsuario(login);

        String valorAtributo = usuario.getAtributos().get(atributo);

        return valorAtributo;
    }
}
