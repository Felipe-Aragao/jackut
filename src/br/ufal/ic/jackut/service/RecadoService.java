package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.NaoHaRecadosException;
import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSeAutoEnviarMensagemException;
import br.ufal.ic.jackut.model.Recado;
import br.ufal.ic.jackut.model.Sessao;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.SessaoRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;

import java.util.List;

/**
 * Serviço responsável pelo envio e leitura de recados.
 */
public class RecadoService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;

    /**
     * Cria o serviço com os repositórios necessários para consultar usuários e sessões.
     *
     * @param usuarioRepository repositório de usuários
     * @param sessaoRepository repositório de sessões
     */
    public RecadoService(UsuarioRepository usuarioRepository, SessaoRepository sessaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
    }

    /**
     * Envia um recado a partir da sessão identificada por `id` para o
     * usuário `destinatario` com o texto `mensagem`.
     *
     * @param id id da sessão do usuário remetente
     * @param destinatario login do usuário destinatário
     * @param mensagem texto do recado
     * @throws UsuarioNaoCadastradoException se a sessão não existir
     * @throws UsuarioNaoPodeSeAutoEnviarMensagemException se o remetente for o mesmo do destinatário
     */
    public void enviarRecado(String id, String destinatario, String mensagem)
            throws UsuarioNaoCadastradoException, UsuarioNaoPodeSeAutoEnviarMensagemException {
        Sessao sessao = sessaoRepository.buscarSessao(id);

        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuarioRemetente = sessao.getUsuario();
        Usuario usuarioDestinatario = usuarioRepository.buscarUsuario(destinatario);

        if (usuarioRemetente.getLogin().equals(destinatario)) {
            throw new UsuarioNaoPodeSeAutoEnviarMensagemException();
        }

        Recado recado = new Recado(usuarioRemetente.getLogin(), usuarioDestinatario.getLogin(), mensagem);

        List<Recado> recadosDestinatario = usuarioDestinatario.getRecados();
        recadosDestinatario.add(recado);

        usuarioDestinatario.setRecados(recadosDestinatario);
    }

    /**
     * Lê o recado mais antigo da fila de recados do usuário
     * associado à sessão `id`.
     *
     * @param id id da sessão do usuário
     * @return o texto do recado lido
     * @throws UsuarioNaoCadastradoException se a sessão não existir
     * @throws NaoHaRecadosException se não existirem recados para o usuário
     */
    public String lerRecado(String id)
            throws UsuarioNaoCadastradoException, NaoHaRecadosException {
        Sessao sessao = sessaoRepository.buscarSessao(id);

        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuario = sessao.getUsuario();
        List<Recado> recados = usuario.getRecados();

        if (recados.isEmpty()) {
            throw new NaoHaRecadosException();
        }

        String mensagem = recados.get(0).getMensagem();

        recados.remove(0);
        usuario.setRecados(recados);

        return mensagem;
    }
}
