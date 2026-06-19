package br.ufal.ic.jackut.service.strategy;

import br.ufal.ic.jackut.exception.EsperandoAceitacaoDoConviteException;
import br.ufal.ic.jackut.exception.FuncaoInvalidaUsuarioInimigoException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoAmigoException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoIdoloException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoInimigoException;
import br.ufal.ic.jackut.exception.UsuarioJaEstaAdicionadoComoPaqueraException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSeAutoAdicionarException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSerFaDeSiMesmoException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSerInimigoDeSiMesmoException;
import br.ufal.ic.jackut.exception.UsuarioNaoPodeSerPaqueraDeSiMesmoException;
import br.ufal.ic.jackut.model.Usuario;

public interface RelacionamentoStrategy {

    void adicionar(Usuario origem, Usuario alvo)
            throws FuncaoInvalidaUsuarioInimigoException,
            UsuarioNaoPodeSeAutoAdicionarException, UsuarioJaEstaAdicionadoComoAmigoException,
            EsperandoAceitacaoDoConviteException, UsuarioJaEstaAdicionadoComoIdoloException,
            UsuarioNaoPodeSerFaDeSiMesmoException, UsuarioJaEstaAdicionadoComoPaqueraException,
            UsuarioNaoPodeSerPaqueraDeSiMesmoException, UsuarioJaEstaAdicionadoComoInimigoException,
            UsuarioNaoPodeSerInimigoDeSiMesmoException;
}
