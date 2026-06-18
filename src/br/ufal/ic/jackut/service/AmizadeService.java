package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.EsperandoAceitacaoDoConviteException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoAmigoException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSeAutoAdicionarException;
import br.ufal.ic.jackut.model.Sessao;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.SessaoRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;

/**
 * Serviço responsável por localizar usuários e delegar regras de amizade ao modelo.
 */
public class AmizadeService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;

    /**
     * Cria o serviço com os repositórios necessários para consultar usuários e sessões.
     *
     * @param usuarioRepository repositório de usuários
     * @param sessaoRepository repositório de sessões
     */
    public AmizadeService(UsuarioRepository usuarioRepository, SessaoRepository sessaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
    }

    /**
     * Envia um pedido de amizade do usuário identificado pela sessão `id`
     * ao usuário `amigo`. O relacionamento só é efetivado quando o outro
     * usuário adicionar de volta.
     *
     * @param id id da sessão do usuário que envia o pedido
     * @param amigo login do usuário alvo do pedido
     * @throws UsuarioNaoCadastradoException se a sessão for inválida ou o usuário alvo não existir
     * @throws UsuarioJaEstaAdicionadoComoAmigoException se já são amigos
     * @throws EsperandoAceitacaoDoConviteException se já existe um convite pendente
     * @throws UsuarioNaoPodeSeAutoAdicionarException se o usuário tentar adicionar a si
     */
    public void adicionarAmigo(String id, String amigo)
            throws UsuarioNaoCadastradoException, UsuarioJaEstaAdicionadoComoAmigoException,
            EsperandoAceitacaoDoConviteException, UsuarioNaoPodeSeAutoAdicionarException {

        Sessao sessao = sessaoRepository.buscarSessao(id);

        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuario = sessao.getUsuario();
        Usuario usuarioAlvo = usuarioRepository.buscarUsuario(amigo);

        usuario.adicionarAmigo(usuarioAlvo);
    }

    /**
     * Verifica se `amigo` faz parte da lista de amigos de `login`.
     *
     * @param login login do usuário que consulta
     * @param amigo login do possível amigo
     * @return true se são amigos, false caso contrário
     * @throws UsuarioNaoCadastradoException se o usuário do `login` não existir
     */
    public boolean ehAmigo(String login, String amigo) throws UsuarioNaoCadastradoException {
        Usuario usuario = usuarioRepository.buscarUsuario(login);
        return usuario.ehAmigoDe(amigo);
    }

    /**
     * Retorna a lista de amigos do usuário no formato {a,b,c}
     *
     * @param login login do usuário
     * @return string contendo os amigos do usuário
     * @throws UsuarioNaoCadastradoException se o usuário não existir
     */
    public String getAmigos(String login) throws UsuarioNaoCadastradoException {
        Usuario usuario = usuarioRepository.buscarUsuario(login);
        return usuario.listarAmigos();
    }
}