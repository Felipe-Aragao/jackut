package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.FuncaoInvalidaUsuarioInimigoException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoIdoloException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSerFaDeSiMesmoException;
import br.ufal.ic.jackut.model.Usuario;

/**
 * Estratégia que aplica as regras de ídolos e fãs entre usuários.
 */
public class IdoloStrategy extends AbstractRelacionamentoStrategy {

    /**
     * Adiciona o alvo como ídolo da origem e registra a origem como fã do alvo.
     *
     * @param origem usuário que será fã
     * @param alvo usuário que será ídolo
     * @throws FuncaoInvalidaUsuarioInimigoException se o alvo marcou a origem como inimiga
     * @throws UsuarioJaEstaAdicionadoComoIdoloException se o ídolo já estiver adicionado
     * @throws UsuarioNaoPodeSerFaDeSiMesmoException se origem e alvo forem o mesmo usuário
     */
    @Override
    public void adicionar(Usuario origem, Usuario alvo)
            throws FuncaoInvalidaUsuarioInimigoException, UsuarioJaEstaAdicionadoComoIdoloException,
            UsuarioNaoPodeSerFaDeSiMesmoException {
        validarAutoRelacionamento(origem, alvo, new UsuarioNaoPodeSerFaDeSiMesmoException());

        validarBloqueioPorInimigo(origem, alvo);

        if (origem.temRelacionamento(Usuario.REL_IDOLOS, alvo.getLogin())) {
            throw new UsuarioJaEstaAdicionadoComoIdoloException();
        }

        origem.adicionarRelacionamento(Usuario.REL_IDOLOS, alvo.getLogin());
        alvo.adicionarRelacionamento(Usuario.REL_FAS, origem.getLogin());
    }
}
