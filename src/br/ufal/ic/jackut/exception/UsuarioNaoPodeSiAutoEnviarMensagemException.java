package br.ufal.ic.jackut.exception;

import br.ufal.ic.jackut.service.UsuarioService;

public class UsuarioNaoPodeSiAutoEnviarMensagemException extends Exception {
    public UsuarioNaoPodeSiAutoEnviarMensagemException() {
        super("Usu�rio n�o pode enviar recado para si mesmo.");
    }
}
