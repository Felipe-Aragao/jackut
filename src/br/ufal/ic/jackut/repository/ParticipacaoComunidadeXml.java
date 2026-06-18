package br.ufal.ic.jackut.repository;

import br.ufal.ic.jackut.model.ParticipacaoComunidade;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParticipacaoComunidadeXml {

    private static final String DIRETORIO = "data";
    private static final String CAMINHO = DIRETORIO + "/participacoes-comunidades.xml";

    public void salvar(List<ParticipacaoComunidade> participacoes) {
        File diretorio = new File(DIRETORIO);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(CAMINHO))) {
            encoder.writeObject(new ArrayList<>(participacoes));
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados em " + CAMINHO + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<ParticipacaoComunidade> carregar() {
        File file = new File(CAMINHO);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(file))) {
            return (List<ParticipacaoComunidade>) decoder.readObject();
        } catch (IOException | ClassCastException e) {
            System.err.println("Erro ao carregar dados de " + CAMINHO + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void apagar() {
        new File(CAMINHO).delete();
    }
}