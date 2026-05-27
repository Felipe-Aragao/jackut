package br.ufal.ic.jackut;

import br.ufal.ic.jackut.exception.*;
import br.ufal.ic.jackut.repository.UsuarioRepository;
import br.ufal.ic.jackut.service.SessaoService;
import br.ufal.ic.jackut.service.UsuarioService;

import java.io.File;

public class Facade {

    private final UsuarioService usuarioService;
    private final SessaoService sessaoService;
    UsuarioRepository usuarioRepository;

    public Facade() {
        this.usuarioRepository = new UsuarioRepository();
        this.usuarioService = new UsuarioService(usuarioRepository);
        this.sessaoService = new SessaoService(usuarioRepository);
    }

    // Sistema

    public void zerarSistema(){
        usuarioRepository.limpar();
        new File("data/usuario.xml").delete();
    }

    public void encerrarSistema(){
        usuarioRepository.save();
    }

    // Usuario
    public String  getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException {
        return usuarioService.getAtributoUsuario(login, atributo);
    }

    public void criarUsuario(String login, String senha, String nome)
            throws ContaJaExisteException, SenhaInvalidaException, LoginInvalidoException {
        usuarioService.criarUsuario(login, senha, nome);
    }

    //Sessão

    public void abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException {
        sessaoService.abrirSessao(login, senha);
    }

}
