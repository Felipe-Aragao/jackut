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

public class MensagemService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;
    private final ComunidadeRepository comunidadeRepository;
    private final ParticipacaoComunidadeRepository participacaoComunidadeRepository;

    public MensagemService(UsuarioRepository usuarioRepository,
                           SessaoRepository sessaoRepository,
                           ComunidadeRepository comunidadeRepository,
                           ParticipacaoComunidadeRepository participacaoComunidadeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
        this.comunidadeRepository = comunidadeRepository;
        this.participacaoComunidadeRepository = participacaoComunidadeRepository;
    }

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

    public String lerMensagem(String id)
            throws UsuarioNaoCadastradoException, NaoHaMensagensException {
        Sessao sessao = sessaoRepository.buscarSessao(id);

        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }

        return sessao.getUsuario().lerMensagemMaisAntiga();
    }
}
