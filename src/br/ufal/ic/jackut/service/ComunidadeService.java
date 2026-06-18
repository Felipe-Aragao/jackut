package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.ComunidadeJaExisteException;
import br.ufal.ic.jackut.exception.ComunidadeNaoExisteException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Comunidade;
import br.ufal.ic.jackut.model.Sessao;
import br.ufal.ic.jackut.repository.ComunidadeRepository;
import br.ufal.ic.jackut.repository.SessaoRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;

public class ComunidadeService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;
    private final ComunidadeRepository comunidadeRepository;

    public ComunidadeService(UsuarioRepository usuarioRepository,
                             SessaoRepository sessaoRepository,
                             ComunidadeRepository comunidadeRepository){

        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
        this.comunidadeRepository = comunidadeRepository;
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

        Comunidade comunidade = new Comunidade(sessao.getUsuario().getLogin(), nome, descricao);

        comunidadeRepository.adicionarComunidade(comunidade);
    }

    public String getDescricaoComunidade(String nome) throws ComunidadeNaoExisteException {
        return comunidadeRepository.buscarComunidade(nome).getDescricao();
    }

    public String getDonoComunidade(String nome) throws ComunidadeNaoExisteException {
        return comunidadeRepository.buscarComunidade(nome).getDono();
    }

    public String getMembrosComunidade(String nome) throws ComunidadeNaoExisteException {
        Comunidade comunidade = comunidadeRepository.buscarComunidade(nome);
        return comunidade.listarMembros();
    }
}