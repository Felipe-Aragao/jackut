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

public class UsuarioRepository {

    private static final String CAMINHO = "data/usuario.xml";

    private final Map<String, Usuario> usuarios;

    public UsuarioRepository() {
        this.usuarios = load();
    }

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

    public void adicionarUsuario(Usuario usuario) {

        usuarios.put(usuario.getLogin(), usuario);
        save();
    }

    public void limpar() {
        usuarios.clear();
    }

    public Usuario buscarUsuario(String login) throws UsuarioNaoCadastradoException {

        Usuario usuario = usuarios.get(login);

        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }

        return usuario;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }
}
