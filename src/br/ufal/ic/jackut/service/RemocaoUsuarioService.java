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

public class RemocaoUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;
    private final ComunidadeRepository comunidadeRepository;
    private final ParticipacaoComunidadeRepository participacaoComunidadeRepository;

    public RemocaoUsuarioService(UsuarioRepository usuarioRepository,
                                 SessaoRepository sessaoRepository,
                                 ComunidadeRepository comunidadeRepository,
                                 ParticipacaoComunidadeRepository participacaoComunidadeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
        this.comunidadeRepository = comunidadeRepository;
        this.participacaoComunidadeRepository = participacaoComunidadeRepository;
    }

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
