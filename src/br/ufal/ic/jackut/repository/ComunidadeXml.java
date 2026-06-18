package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.model.Comunidade;
import br.ufal.ic.jackut.model.Usuario;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ComunidadeXml {

    private static final String DIRETORIO = "data";
    private static final String CAMINHO = DIRETORIO + "/comunidades.xml";


    public void salvar(Map<String, Comunidade> comunidades) {
        File diretorio = new File(DIRETORIO);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(CAMINHO))) {
            encoder.writeObject(new HashMap<>(comunidades));
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados em " + CAMINHO + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Comunidade> carregar() {
        File file = new File(CAMINHO);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(file))) {
             return (Map<String, Comunidade>) decoder.readObject();
        } catch (IOException | ClassCastException e) {
            System.err.println("Erro ao carregar dados de " + CAMINHO + ": " + e.getMessage());
            return new HashMap<>();
        }
    }

    public void apagar() {
        new File(CAMINHO).delete();
    }
}
