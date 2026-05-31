package br.ufal.ic.jackut;

import br.ufal.ic.jackut.exception.*;
import br.ufal.ic.jackut.repository.SessaoRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;
import br.ufal.ic.jackut.service.SessaoService;
import br.ufal.ic.jackut.service.UsuarioService;

import java.io.File;

/**
 * Facade que expõe a API pública do sistema para uso em testes.
 * Agrupa os serviços de usuário e sessão e delega operações a eles.
 */
public class Facade {

    private final UsuarioService usuarioService;
    private final SessaoService sessaoService;
    UsuarioRepository usuarioRepository;
    SessaoRepository sessaoRepository;

    public Facade() {
        this.usuarioRepository = new UsuarioRepository();
        this.sessaoRepository = new SessaoRepository();
        this.usuarioService = new UsuarioService(usuarioRepository, sessaoRepository);
        this.sessaoService = new SessaoService(usuarioRepository, sessaoRepository);
    }

    // Sistema

    /**
     * Zera o sistema removendo todos os usuários em memória e excluindo
     * o arquivo de persistência.
     */
    public void zerarSistema(){
        usuarioRepository.limpar();
        new File("data/usuario.xml").delete();
    }

    /**
     * Salva o estado atual do repositório de usuários no disco.
     */
    public void encerrarSistema(){
        usuarioRepository.save();
    }

    // Usuario
    /**
     * Retorna o valor de um atributo do usuário identificado por `login`.
     *
     * @param login login do usuário
     * @param atributo o nome do atributo a ser buscado
     * @return o valor do atributo
     * @throws UsuarioNaoCadastradoException se o usuário não existir
     * @throws AtributoNaoPreenchidoException se o atributo não estiver preenchido
     */
    public String getAtributoUsuario(String login, String atributo)
            throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        return usuarioService.getAtributoUsuario(login, atributo);
    }

    /**
     * Cria um usuário com `login`, `senha` e `nome`.
     *
     * @param login login desejado
     * @param senha senha do usuário
     * @param nome nome do usuário
     * @throws ContaJaExisteException se já existir usuário com o mesmo login
     * @throws SenhaInvalidaException se a senha for inválida
     * @throws LoginInvalidoException se o login for inválido
     */
    public void criarUsuario(String login, String senha, String nome)
            throws ContaJaExisteException, SenhaInvalidaException, LoginInvalidoException {
        usuarioService.criarUsuario(login, senha, nome);
    }

    //Sessão

    /**
     * Abre uma sessão para o usuário com `login` e `senha`.
     *
     * @param login login do usuário
     * @param senha senha do usuário
     * @return o id da sessão
     * @throws LoginOuSenhaInvalidoException se o login ou senha estiverem inválidos
     */
    public String abrirSessao(String login, String senha)
            throws LoginOuSenhaInvalidoException {
        return sessaoService.abrirSessao(login, senha);
    }

    public void editarPerfil(String id, String atributo, String valor)
            throws UsuarioNaoCadastradoException {
        usuarioService.editarPerfil(id, atributo, valor);
    }

    /**
     * Envia um pedido de amizade do usuário identificado pela sessão `id`
     * ao usuário `amigo`. O relacionamento só é efetivado quando o outro
     * usuário adicionar de volta.
     *
     * @param id id da sessão do usuário que envia o pedido
     * @param amigo login do usuário alvo do pedido
     * @throws UsuarioNaoCadastradoException se a sessão for inválida
     * @throws UsuarioJaEstaAdicionadoComoAmigoException se já são amigos
     * @throws EsperandoAceitacaoDoConviteException se já existe um convite pendente
     * @throws UsuarioNaoPodeSiAutoAdicionarException se o usuário tentar adicionar a si mesmo
     */
    public void adicionarAmigo(String id, String amigo)
            throws UsuarioNaoCadastradoException, UsuarioJaEstaAdicionadoComoAmigoException,
            EsperandoAceitacaoDoConviteException, UsuarioNaoPodeSiAutoAdicionarException {
        usuarioService.adicionarAmigo(id, amigo);
    }

    /**
     * Verifica se `amigo` faz parte da lista de amigos de `login`.
     *
     * @param login login do usuário que consulta
     * @param amigo login do possível amigo
     * @return true se são amigos, false caso contrário
     * @throws UsuarioNaoCadastradoException se o usuário do `login` não existir
     */
    public boolean ehAmigo(String login ,String amigo)
            throws UsuarioNaoCadastradoException {
        return usuarioService.ehAmigo(login, amigo);
    }

    /**
     * Retorna a lista de amigos do usuário no formato {a,b,c}
     *
     * @param login login do usuário
     * @return string contendo os amigos do usuário
     * @throws UsuarioNaoCadastradoException se o usuário não existir
     */
    public String getAmigos(String login) throws UsuarioNaoCadastradoException {
        return usuarioService.getAmigos(login);
    }

}
