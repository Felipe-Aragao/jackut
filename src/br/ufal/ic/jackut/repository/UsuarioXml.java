package br.ufal.ic.jackut.repository;

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
 * Responsável pela persistência XML dos usuários.
 */
public class UsuarioXml {

    private static final String DIRETORIO = "data";
    private static final String CAMINHO = DIRETORIO + "/usuario.xml";

    /**
     * Salva o mapa de usuários no arquivo XML de persistência.
     *
     * @param usuarios mapa de login para usuário que será persistido
     */
    public void salvar(Map<String, Usuario> usuarios) {
        File diretorio = new File(DIRETORIO);
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
     * Carrega os usuários persistidos em XML.
     *
     * @return mapa de login para usuário carregado do arquivo, ou mapa vazio se não houver dados
     */
    @SuppressWarnings("unchecked")
    public Map<String, Usuario> carregar() {
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
     * Apaga o arquivo XML usado para persistir os usuários.
     */
    public void apagar() {
        new File(CAMINHO).delete();
    }
}
