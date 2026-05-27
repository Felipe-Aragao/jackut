package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Usuario;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Repositório responsável por persistir e recuperar instâncias de `Usuario`.
 * Os dados são armazenados em XML no caminho definido por `CAMINHO`.
 */
public class UsuarioRepository {

    private static final String CAMINHO = "data/usuario.xml";

    private final Map<String, Usuario> usuarios;

    /**
     * Inicializa o repositório carregando os usuários persistidos, se houver.
     */
    public UsuarioRepository() {
        this.usuarios = load();
    }

    /**
     * Persiste o mapa de usuários atual em disco (XML).
     */
    public void save() {
        File diretorio = new File("data");
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(CAMINHO))) {
            encoder.writeObject(new HashMap<>(usuarios));
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados em " + CAMINHO + ": " + e.getMessage());
        }
    }

    /**
     * Carrega o mapa de usuários a partir do arquivo XML. Retorna um mapa vazio
     * se o arquivo não existir ou ocorrer erro na leitura.
     *
     * @return mapa de login->Usuario carregado do XML
     */
    @SuppressWarnings("unchecked")
    public Map<String, Usuario> load() {
        File file = new File(CAMINHO);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(file))) {
            Object data = decoder.readObject();
            return data instanceof Map ? (Map<String, Usuario>) data : new HashMap<>();
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados de " + CAMINHO + ": " + e.getMessage());
            return new HashMap<>();
        }
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
