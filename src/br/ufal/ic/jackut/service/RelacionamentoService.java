package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.EsperandoAceitacaoDoConviteException;
import br.ufal.ic.jackut.exception.FuncaoInvalidaUsuarioInimigoException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoAmigoException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoIdoloException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoInimigoException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoPaqueraException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSeAutoAdicionarException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSerFaDeSiMesmoException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSerInimigoDeSiMesmoException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSerPaqueraDeSiMesmoException;
import br.ufal.ic.jackut.model.Sessao;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.SessaoRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;
import br.ufal.ic.jackut.service.observer.PaqueraMutuaObserver;
import br.ufal.ic.jackut.service.observer.PaqueraObserver;
import br.ufal.ic.jackut.service.strategy.AmizadeStrategy;
import br.ufal.ic.jackut.service.strategy.IdoloStrategy;
import br.ufal.ic.jackut.service.strategy.InimigoStrategy;
import br.ufal.ic.jackut.service.strategy.PaqueraStrategy;

import java.util.ArrayList;
import java.util.List;

public class RelacionamentoService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;
    private final AmizadeStrategy amizadeStrategy;
    private final IdoloStrategy idoloStrategy;
    private final PaqueraStrategy paqueraStrategy;
    private final InimigoStrategy inimigoStrategy;
    private final List<PaqueraObserver> paqueraObservers = new ArrayList<>();

    public RelacionamentoService(UsuarioRepository usuarioRepository, SessaoRepository sessaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
        this.amizadeStrategy = new AmizadeStrategy();
        this.idoloStrategy = new IdoloStrategy();
        this.paqueraStrategy = new PaqueraStrategy();
        this.inimigoStrategy = new InimigoStrategy();
        this.paqueraObservers.add(new PaqueraMutuaObserver());
    }

    public void adicionarAmigo(String id, String amigo)
            throws UsuarioNaoCadastradoException, FuncaoInvalidaUsuarioInimigoException,
            UsuarioNaoPodeSeAutoAdicionarException, UsuarioJaEstaAdicionadoComoAmigoException,
            EsperandoAceitacaoDoConviteException {
        Usuario origem = buscarUsuarioDaSessao(id);
        Usuario alvo = usuarioRepository.buscarUsuario(amigo);

        amizadeStrategy.adicionar(origem, alvo);
    }

    public boolean ehAmigo(String login, String amigo) throws UsuarioNaoCadastradoException {
        return usuarioRepository.buscarUsuario(login).temRelacionamento(Usuario.REL_AMIGOS, amigo);
    }

    public String getAmigos(String login) throws UsuarioNaoCadastradoException {
        return usuarioRepository.buscarUsuario(login).listarRelacionamentos(Usuario.REL_AMIGOS);
    }

    public void adicionarIdolo(String id, String idolo)
            throws UsuarioNaoCadastradoException, FuncaoInvalidaUsuarioInimigoException,
            UsuarioJaEstaAdicionadoComoIdoloException, UsuarioNaoPodeSerFaDeSiMesmoException {
        Usuario origem = buscarUsuarioDaSessao(id);
        Usuario alvo = usuarioRepository.buscarUsuario(idolo);

        idoloStrategy.adicionar(origem, alvo);
    }

    public boolean ehFa(String login, String idolo) throws UsuarioNaoCadastradoException {
        return usuarioRepository.buscarUsuario(login).temRelacionamento(Usuario.REL_IDOLOS, idolo);
    }

    public String getFas(String login) throws UsuarioNaoCadastradoException {
        return usuarioRepository.buscarUsuario(login).listarRelacionamentos(Usuario.REL_FAS);
    }

    public void adicionarPaquera(String id, String paquera)
            throws UsuarioNaoCadastradoException, FuncaoInvalidaUsuarioInimigoException,
            UsuarioJaEstaAdicionadoComoPaqueraException, UsuarioNaoPodeSerPaqueraDeSiMesmoException {
        Usuario origem = buscarUsuarioDaSessao(id);
        Usuario alvo = usuarioRepository.buscarUsuario(paquera);

        paqueraStrategy.adicionar(origem, alvo);
        notificarPaquera(origem, alvo);
    }

    public boolean ehPaquera(String id, String paquera) throws UsuarioNaoCadastradoException {
        return buscarUsuarioDaSessao(id).temRelacionamento(Usuario.REL_PAQUERAS, paquera);
    }

    public String getPaqueras(String id) throws UsuarioNaoCadastradoException {
        return buscarUsuarioDaSessao(id).listarRelacionamentos(Usuario.REL_PAQUERAS);
    }

    public void adicionarInimigo(String id, String inimigo)
            throws UsuarioNaoCadastradoException, UsuarioJaEstaAdicionadoComoInimigoException,
            UsuarioNaoPodeSerInimigoDeSiMesmoException {
        Usuario origem = buscarUsuarioDaSessao(id);
        Usuario alvo = usuarioRepository.buscarUsuario(inimigo);

        inimigoStrategy.adicionar(origem, alvo);
    }

    private Usuario buscarUsuarioDaSessao(String id) throws UsuarioNaoCadastradoException {
        Sessao sessao = sessaoRepository.buscarSessao(id);

        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }

        return sessao.getUsuario();
    }

    private void notificarPaquera(Usuario origem, Usuario alvo) {
        for (PaqueraObserver observer : paqueraObservers) {
            observer.paqueraAdicionada(origem, alvo);
        }
    }
}
