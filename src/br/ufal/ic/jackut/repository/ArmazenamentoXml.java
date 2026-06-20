package br.ufal.ic.jackut.repository;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Function;

/**
 * Responsável pela persistência XML em arquivo.
 *
 * @param <T> tipo dos dados persistidos
 */
public class ArmazenamentoXml<T> {

    private static final String DIRETORIO = "data";

    private final String caminho;
    private final T valorVazio;
    private final Function<T, T> copia;

    /**
     * Inicializa o armazenamento XML para um arquivo dentro do diretório de dados.
     *
     * @param nomeArquivo nome do arquivo XML
     * @param valorVazio valor usado como base quando não houver dados
     * @param copia função usada para criar uma cópia defensiva dos dados
     */
    public ArmazenamentoXml(String nomeArquivo, T valorVazio, Function<T, T> copia) {
        this.caminho = DIRETORIO + "/" + nomeArquivo;
        this.valorVazio = valorVazio;
        this.copia = copia;
    }

    /**
     * Salva os dados no arquivo XML de persistência.
     *
     * @param dados dados que serão persistidos
     */
    public void salvar(T dados) {
        File diretorio = new File(DIRETORIO);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(caminho))) {
            encoder.writeObject(copia.apply(dados));
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados em " + caminho + ": " + e.getMessage());
        }
    }

    /**
     * Carrega os dados persistidos em XML.
     *
     * @return dados carregados do arquivo, ou valor vazio se não houver dados
     */
    @SuppressWarnings("unchecked")
    public T carregar() {
        File file = new File(caminho);
        if (!file.exists()) {
            return copia.apply(valorVazio);
        }

        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(file))) {
            return (T) decoder.readObject();
        } catch (IOException | ClassCastException e) {
            System.err.println("Erro ao carregar dados de " + caminho + ": " + e.getMessage());
            return copia.apply(valorVazio);
        }
    }

    /**
     * Apaga o arquivo XML usado para persistência.
     */
    public void apagar() {
        new File(caminho).delete();
    }
}