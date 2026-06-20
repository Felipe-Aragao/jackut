package br.ufal.ic.jackut.service;

import br.ufal.ic.jackut.exception.UsuarioNaoCadastradoException;
import br.ufal.ic.jackut.model.Sessao;
import br.ufal.ic.jackut.model.Usuario;
import br.ufal.ic.jackut.repository.ComunidadeRepository;
import br.ufal.ic.jackut.repository.ParticipacaoComunidadeRepository;
import br.ufal.ic.jackut.repository.SessaoRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Serviço responsável pela remoção de usuários e dados relacionados.
 */
public class RemocaoUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;
    private final ComunidadeRepository comunidadeRepository;
    private final ParticipacaoComunidadeRepository participacaoComunidadeRepository;

    /**
     * Cria o serviço com os repositórios necessários para remoção de usuários.
     *
     * @param usuarioRepository repositório de usuários
     * @param sessaoRepository repositório de sessões
     * @param comunidadeRepository repositório de comunidades
     * @param participacaoComunidadeRepository repositório de participações em comunidades
     */
    public RemocaoUsuarioService(UsuarioRepository usuarioRepository,
                                 SessaoRepository sessaoRepository,
                                 ComunidadeRepository comunidadeRepository,
                                 ParticipacaoComunidadeRepository participacaoComunidadeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
        this.comunidadeRepository = comunidadeRepository;
        this.participacaoComunidadeRepository = participacaoComunidadeRepository;
    }

    /**
     * Remove o usuário associado à sessão informada e limpa seus vínculos.
     *
     * @param id id da sessão do usuário a remover
     * @throws UsuarioNaoCadastradoException se a sessão não existir ou o usuário não estiver cadastrado
     */
    public void removerUsuario(String id) throws UsuarioNaoCadastradoException {
        Sessao sessao = sessaoRepository.buscarSessao(id);

        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }

        String loginRemovido = sessao.getUsuario().getLogin();
        List<String> comunidadesDoUsuario = comunidadeRepository.listarComunidadesDoDono(loginRemovido);
        Set<String> comunidadesRemovidas = new LinkedHashSet<>(comunidadesDoUsuario);

        limparUsuariosRestantes(loginRemovido);
        removerComunidades(comunidadesRemovidas);
        participacaoComunidadeRepository.removerPorLogin(loginRemovido);
        participacaoComunidadeRepository.removerPorComunidades(comunidadesRemovidas);
        sessaoRepository.removerSessoesDoUsuario(loginRemovido);
        usuarioRepository.removerUsuario(loginRemovido);
    }

    private void limparUsuariosRestantes(String loginRemovido) {
        for (Usuario usuario : usuarioRepository.getUsuarios().values()) {
            if (!loginRemovido.equals(usuario.getLogin())) {
                usuario.removerRelacionamentosCom(loginRemovido);
                usuario.removerRecadosEnvolvendo(loginRemovido);
                usuario.removerMensagensEnviadasPor(loginRemovido);
            }
        }
    }

    private void removerComunidades(Set<String> comunidadesRemovidas) {
        for (String comunidade : comunidadesRemovidas) {
            comunidadeRepository.removerComunidade(comunidade);
        }
    }
}
