package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.ComunidadeJaExisteException;
import br.ufal.ic.jackut.exception.ComunidadeNaoExisteException;
import br.ufal.ic.jackut.exception.UsuarioJaFazParteDaComunidadeException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Comunidade;
import br.ufal.ic.jackut.model.Sessao;
import br.ufal.ic.jackut.repository.ComunidadeRepository;
import br.ufal.ic.jackut.repository.ParticipacaoComunidadeRepository;
import br.ufal.ic.jackut.repository.SessaoRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;

import java.util.List;

public class ComunidadeService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;
    private final ComunidadeRepository comunidadeRepository;
    private final ParticipacaoComunidadeRepository participacaoComunidadeRepository;

    public ComunidadeService(UsuarioRepository usuarioRepository,
                             SessaoRepository sessaoRepository,
                             ComunidadeRepository comunidadeRepository,
                             ParticipacaoComunidadeRepository participacaoComunidadeRepository){

        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
        this.comunidadeRepository = comunidadeRepository;
        this.participacaoComunidadeRepository = participacaoComunidadeRepository;
    }

    public void criarComunidade(String id, String nome, String descricao)
            throws UsuarioNaoCadastradoException, ComunidadeJaExisteException {
        Sessao sessao = sessaoRepository.buscarSessao(id);

        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }

        if (comunidadeRepository.existeComunidade(nome)) {
            throw new ComunidadeJaExisteException();
        }

        String login = sessao.getUsuario().getLogin();
        Comunidade comunidade = new Comunidade(login, nome, descricao);

        comunidadeRepository.adicionarComunidade(comunidade);
        participacaoComunidadeRepository.adicionar(login, nome);
    }

    public String getDescricaoComunidade(String nome) throws ComunidadeNaoExisteException {
        return comunidadeRepository.buscarComunidade(nome).getDescricao();
    }

    public String getDonoComunidade(String nome) throws ComunidadeNaoExisteException {
        return comunidadeRepository.buscarComunidade(nome).getDono();
    }

    public String getMembrosComunidade(String nome) throws ComunidadeNaoExisteException {
        comunidadeRepository.buscarComunidade(nome);
        return listar(participacaoComunidadeRepository.listarMembros(nome));
    }

    public String getComunidades(String login)
            throws UsuarioNaoCadastradoException {
        usuarioRepository.buscarUsuario(login);
        return listar(participacaoComunidadeRepository.listarComunidades(login));
    }

    public void adicionarComunidade(String id, String nome)
            throws UsuarioNaoCadastradoException, ComunidadeNaoExisteException,
            UsuarioJaFazParteDaComunidadeException {
        Sessao sessao = sessaoRepository.buscarSessao(id);

        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }

        comunidadeRepository.buscarComunidade(nome);
        String login = sessao.getUsuario().getLogin();

        if (participacaoComunidadeRepository.existe(login, nome)) {
            throw new UsuarioJaFazParteDaComunidadeException();
        }

        participacaoComunidadeRepository.adicionar(login, nome);
    }

    private String listar(List<String> valores) {
        return "{" + String.join(",", valores) + "}";
    }
}