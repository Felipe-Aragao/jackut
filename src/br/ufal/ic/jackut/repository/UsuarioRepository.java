package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Usuario;

import java.util.HashMap;
import java.util.Map;

/**
 * Repositório responsável por manter e recuperar instâncias de `Usuario`.
 */
public class UsuarioRepository {

    private final Map<String, Usuario> usuarios;
    private final UsuarioXml armazenamento;

    /**
     * Inicializa o repositório carregando os usuários persistidos, se houver.
     */
    public UsuarioRepository() {
        this.armazenamento = new UsuarioXml();
        this.usuarios = armazenamento.carregar();
    }

    /**
     * Persiste o mapa de usuários atual em disco (XML).
     */
    public void save() {
        armazenamento.salvar(usuarios);
    }

    /**
     * Adiciona um usuário ao repositório.
     *
     * @param usuario o usuário a adicionar
     */
    public void adicionarUsuario(Usuario usuario) {

        usuarios.put(usuario.getLogin(), usuario);
    }

    /**
     * Remove todos os usuários mantidos em memória.
     */
    public void limpar() {
        usuarios.clear();
    }

    /**
     * Remove os dados persistidos em disco.
     */
    public void apagarPersistencia() {
        armazenamento.apagar();
    }

    /**
     * Busca um usuário pelo login.
     *
     * @param login login do usuário
     * @return o `Usuario` encontrado
     * @throws UsuarioNaoCadastradoException se não existir usuário com o login
     */
    public Usuario buscarUsuario(String login) throws UsuarioNaoCadastradoException {

        Usuario usuario = usuarios.get(login);

        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }

        return usuario;
    }

    /**
     * Retorna uma cópia do mapa de usuários.
     *
     * @return mapa de login->Usuario
     */
    public Map<String, Usuario> getUsuarios() {
        return new HashMap<>(usuarios);
    }
}
