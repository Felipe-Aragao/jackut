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

/**
 * Serviço responsável pelas operações de relacionamento entre usuários.
 */
public class RelacionamentoService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;
    private final AmizadeStrategy amizadeStrategy;
    private final IdoloStrategy idoloStrategy;
    private final PaqueraStrategy paqueraStrategy;
    private final InimigoStrategy inimigoStrategy;
    private final List<PaqueraObserver> paqueraObservers = new ArrayList<>();

    /**
     * Cria o serviço com os repositórios e estratégias de relacionamento.
     *
     * @param usuarioRepository repositório de usuários
     * @param sessaoRepository repositório de sessões
     */
    public RelacionamentoService(UsuarioRepository usuarioRepository, SessaoRepository sessaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
        this.amizadeStrategy = new AmizadeStrategy();
        this.idoloStrategy = new IdoloStrategy();
        this.paqueraStrategy = new PaqueraStrategy();
        this.inimigoStrategy = new InimigoStrategy();
        this.paqueraObservers.add(new PaqueraMutuaObserver());
    }

    /**
     * Envia ou aceita um convite de amizade para o usuário informado.
     *
     * @param id id da sessão do usuário de origem
     * @param amigo login do usuário alvo da amizade
     * @throws UsuarioNaoCadastradoException se a sessão ou o usuário alvo não existir
     * @throws FuncaoInvalidaUsuarioInimigoException se o alvo marcou a origem como inimiga
     * @throws UsuarioNaoPodeSeAutoAdicionarException se o usuário tentar adicionar a si mesmo
     * @throws UsuarioJaEstaAdicionadoComoAmigoException se a amizade já existir
     * @throws EsperandoAceitacaoDoConviteException se já houver convite pendente para a origem
     */
    public void adicionarAmigo(String id, String amigo)
            throws UsuarioNaoCadastradoException, FuncaoInvalidaUsuarioInimigoException,
            UsuarioNaoPodeSeAutoAdicionarException, UsuarioJaEstaAdicionadoComoAmigoException,
            EsperandoAceitacaoDoConviteException {
        Usuario origem = buscarUsuarioDaSessao(id);
        Usuario alvo = usuarioRepository.buscarUsuario(amigo);

        amizadeStrategy.adicionar(origem, alvo);
    }

    /**
     * Verifica se um usuário possui outro usuário como amigo.
     *
     * @param login login do usuário consultado
     * @param amigo login do possível amigo
     * @return true se o relacionamento de amizade existir
     * @throws UsuarioNaoCadastradoException se o usuário consultado não existir
     */
    public boolean ehAmigo(String login, String amigo) throws UsuarioNaoCadastradoException {
        return usuarioRepository.buscarUsuario(login).temRelacionamento(Usuario.REL_AMIGOS, amigo);
    }

    /**
     * Retorna os amigos do usuário no formato esperado pela fachada.
     *
     * @param login login do usuário
     * @return amigos no formato {login1,login2}
     * @throws UsuarioNaoCadastradoException se o usuário não existir
     */
    public String getAmigos(String login) throws UsuarioNaoCadastradoException {
        return usuarioRepository.buscarUsuario(login).listarRelacionamentos(Usuario.REL_AMIGOS);
    }

    /**
     * Adiciona um usuário como ídolo do usuário associado à sessão.
     *
     * @param id id da sessão do usuário fã
     * @param idolo login do usuário ídolo
     * @throws UsuarioNaoCadastradoException se a sessão ou o ídolo não existir
     * @throws FuncaoInvalidaUsuarioInimigoException se o ídolo marcou a origem como inimiga
     * @throws UsuarioJaEstaAdicionadoComoIdoloException se o ídolo já estiver adicionado
     * @throws UsuarioNaoPodeSerFaDeSiMesmoException se o usuário tentar ser fã de si mesmo
     */
    public void adicionarIdolo(String id, String idolo)
            throws UsuarioNaoCadastradoException, FuncaoInvalidaUsuarioInimigoException,
            UsuarioJaEstaAdicionadoComoIdoloException, UsuarioNaoPodeSerFaDeSiMesmoException {
        Usuario origem = buscarUsuarioDaSessao(id);
        Usuario alvo = usuarioRepository.buscarUsuario(idolo);

        idoloStrategy.adicionar(origem, alvo);
    }

    /**
     * Verifica se um usuário é fã do ídolo informado.
     *
     * @param login login do usuário fã
     * @param idolo login do usuário ídolo
     * @return true se o usuário for fã do ídolo
     * @throws UsuarioNaoCadastradoException se o usuário fã não existir
     */
    public boolean ehFa(String login, String idolo) throws UsuarioNaoCadastradoException {
        return usuarioRepository.buscarUsuario(login).temRelacionamento(Usuario.REL_IDOLOS, idolo);
    }

    /**
     * Retorna os fãs do usuário no formato esperado pela fachada.
     *
     * @param login login do usuário idolatrado
     * @return fãs no formato {login1,login2}
     * @throws UsuarioNaoCadastradoException se o usuário não existir
     */
    public String getFas(String login) throws UsuarioNaoCadastradoException {
        return usuarioRepository.buscarUsuario(login).listarRelacionamentos(Usuario.REL_FAS);
    }

    /**
     * Adiciona um usuário como paquera do usuário associado à sessão.
     *
     * @param id id da sessão do usuário de origem
     * @param paquera login do usuário paquera
     * @throws UsuarioNaoCadastradoException se a sessão ou a paquera não existir
     * @throws FuncaoInvalidaUsuarioInimigoException se a paquera marcou a origem como inimiga
     * @throws UsuarioJaEstaAdicionadoComoPaqueraException se a paquera já estiver adicionada
     * @throws UsuarioNaoPodeSerPaqueraDeSiMesmoException se o usuário tentar paquerar a si mesmo
     */
    public void adicionarPaquera(String id, String paquera)
            throws UsuarioNaoCadastradoException, FuncaoInvalidaUsuarioInimigoException,
            UsuarioJaEstaAdicionadoComoPaqueraException, UsuarioNaoPodeSerPaqueraDeSiMesmoException {
        Usuario origem = buscarUsuarioDaSessao(id);
        Usuario alvo = usuarioRepository.buscarUsuario(paquera);

        paqueraStrategy.adicionar(origem, alvo);
        notificarPaquera(origem, alvo);
    }

    /**
     * Verifica se a paquera informada pertence ao usuário associado à sessão.
     *
     * @param id id da sessão do usuário
     * @param paquera login da possível paquera
     * @return true se a paquera estiver adicionada
     * @throws UsuarioNaoCadastradoException se a sessão não existir
     */
    public boolean ehPaquera(String id, String paquera) throws UsuarioNaoCadastradoException {
        return buscarUsuarioDaSessao(id).temRelacionamento(Usuario.REL_PAQUERAS, paquera);
    }

    /**
     * Retorna as paqueras do usuário associado à sessão.
     *
     * @param id id da sessão do usuário
     * @return paqueras no formato {login1,login2}
     * @throws UsuarioNaoCadastradoException se a sessão não existir
     */
    public String getPaqueras(String id) throws UsuarioNaoCadastradoException {
        return buscarUsuarioDaSessao(id).listarRelacionamentos(Usuario.REL_PAQUERAS);
    }

    /**
     * Adiciona um usuário como inimigo do usuário associado à sessão.
     *
     * @param id id da sessão do usuário de origem
     * @param inimigo login do usuário inimigo
     * @throws UsuarioNaoCadastradoException se a sessão ou o inimigo não existir
     * @throws UsuarioJaEstaAdicionadoComoInimigoException se o inimigo já estiver adicionado
     * @throws UsuarioNaoPodeSerInimigoDeSiMesmoException se o usuário tentar ser inimigo de si mesmo
     */
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
