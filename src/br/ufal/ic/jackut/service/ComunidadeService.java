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

/**
 * Serviço responsável pelas operações de comunidades e seus membros.
 */
public class ComunidadeService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;
    private final ComunidadeRepository comunidadeRepository;
    private final ParticipacaoComunidadeRepository participacaoComunidadeRepository;

    /**
     * Cria o serviço com os repositórios necessários para comunidades.
     *
     * @param usuarioRepository repositório de usuários
     * @param sessaoRepository repositório de sessões
     * @param comunidadeRepository repositório de comunidades
     * @param participacaoComunidadeRepository repositório de participações em comunidades
     */
    public ComunidadeService(UsuarioRepository usuarioRepository,
                             SessaoRepository sessaoRepository,
                             ComunidadeRepository comunidadeRepository,
                             ParticipacaoComunidadeRepository participacaoComunidadeRepository){

        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
        this.comunidadeRepository = comunidadeRepository;
        this.participacaoComunidadeRepository = participacaoComunidadeRepository;
    }

    /**
     * Cria uma comunidade para o usuário associado à sessão informada.
     *
     * @param id id da sessão do usuário dono
     * @param nome nome da comunidade
     * @param descricao descrição da comunidade
     * @throws UsuarioNaoCadastradoException se a sessão não existir
     * @throws ComunidadeJaExisteException se já existir comunidade com o mesmo nome
     */
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

    /**
     * Retorna a descrição da comunidade informada.
     *
     * @param nome nome da comunidade
     * @return descrição da comunidade
     * @throws ComunidadeNaoExisteException se a comunidade não existir
     */
    public String getDescricaoComunidade(String nome) throws ComunidadeNaoExisteException {
        return comunidadeRepository.buscarComunidade(nome).getDescricao();
    }

    /**
     * Retorna o login do dono da comunidade informada.
     *
     * @param nome nome da comunidade
     * @return login do dono da comunidade
     * @throws ComunidadeNaoExisteException se a comunidade não existir
     */
    public String getDonoComunidade(String nome) throws ComunidadeNaoExisteException {
        return comunidadeRepository.buscarComunidade(nome).getDono();
    }

    /**
     * Retorna os membros da comunidade no formato esperado pela fachada.
     *
     * @param nome nome da comunidade
     * @return membros no formato {login1,login2}
     * @throws ComunidadeNaoExisteException se a comunidade não existir
     */
    public String getMembrosComunidade(String nome) throws ComunidadeNaoExisteException {
        comunidadeRepository.buscarComunidade(nome);
        return listar(participacaoComunidadeRepository.listarMembros(nome));
    }

    /**
     * Retorna as comunidades das quais o usuário participa.
     *
     * @param login login do usuário
     * @return comunidades no formato {nome1,nome2}
     * @throws UsuarioNaoCadastradoException se o usuário não existir
     */
    public String getComunidades(String login)
            throws UsuarioNaoCadastradoException {
        usuarioRepository.buscarUsuario(login);
        return listar(participacaoComunidadeRepository.listarComunidades(login));
    }

    /**
     * Adiciona o usuário associado à sessão informada em uma comunidade.
     *
     * @param id id da sessão do usuário
     * @param nome nome da comunidade
     * @throws UsuarioNaoCadastradoException se a sessão não existir
     * @throws ComunidadeNaoExisteException se a comunidade não existir
     * @throws UsuarioJaFazParteDaComunidadeException se o usuário já participar da comunidade
     */
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