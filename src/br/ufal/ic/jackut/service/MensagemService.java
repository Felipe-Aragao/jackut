package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.ComunidadeNaoExisteException;
import br.ufal.ic.jackut.exception.NaoHaMensagensException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Sessao;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.ComunidadeRepository;
import br.ufal.ic.jackut.repository.ParticipacaoComunidadeRepository;
import br.ufal.ic.jackut.repository.SessaoRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;

/**
 * Serviço responsável pelo envio e leitura de mensagens em comunidades.
 */
public class MensagemService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;
    private final ComunidadeRepository comunidadeRepository;
    private final ParticipacaoComunidadeRepository participacaoComunidadeRepository;

    /**
     * Cria o serviço com os repositórios necessários para mensagens em comunidades.
     *
     * @param usuarioRepository repositório de usuários
     * @param sessaoRepository repositório de sessões
     * @param comunidadeRepository repositório de comunidades
     * @param participacaoComunidadeRepository repositório de participações em comunidades
     */
    public MensagemService(UsuarioRepository usuarioRepository,
                           SessaoRepository sessaoRepository,
                           ComunidadeRepository comunidadeRepository,
                           ParticipacaoComunidadeRepository participacaoComunidadeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
        this.comunidadeRepository = comunidadeRepository;
        this.participacaoComunidadeRepository = participacaoComunidadeRepository;
    }

    /**
     * Envia uma mensagem para todos os membros da comunidade informada.
     *
     * @param id id da sessão do usuário remetente
     * @param comunidade nome da comunidade destinatária
     * @param mensagem texto da mensagem
     * @throws UsuarioNaoCadastradoException se a sessão for inválida ou algum membro não existir
     * @throws ComunidadeNaoExisteException se a comunidade não existir
     */
    public void enviarMensagem(String id, String comunidade, String mensagem)
            throws UsuarioNaoCadastradoException, ComunidadeNaoExisteException {
        Sessao sessao = sessaoRepository.buscarSessao(id);

        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }

        comunidadeRepository.buscarComunidade(comunidade);

        String remetente = sessao.getUsuario().getLogin();
        for (String login : participacaoComunidadeRepository.listarMembros(comunidade)) {
            Usuario membro = usuarioRepository.buscarUsuario(login);
            membro.receberMensagem(remetente, mensagem);
        }
    }

    /**
     * Lê a mensagem mais antiga do usuário associado à sessão informada.
     *
     * @param id id da sessão do usuário
     * @return texto da mensagem lida
     * @throws UsuarioNaoCadastradoException se a sessão não existir
     * @throws NaoHaMensagensException se não existirem mensagens para o usuário
     */
    public String lerMensagem(String id)
            throws UsuarioNaoCadastradoException, NaoHaMensagensException {
        Sessao sessao = sessaoRepository.buscarSessao(id);

        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }

        return sessao.getUsuario().lerMensagemMaisAntiga();
    }
}
