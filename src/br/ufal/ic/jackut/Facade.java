package br.ufal.ic.jackut;

import br.ufal.ic.jackut.exception.*;
import br.ufal.ic.jackut.repository.ComunidadeRepository;
import br.ufal.ic.jackut.repository.ParticipacaoComunidadeRepository;
import br.ufal.ic.jackut.repository.SessaoRepository;
import br.ufal.ic.jackut.repository.UsuarioRepository;
import br.ufal.ic.jackut.service.*;

/**
 * Facade que expoe a API publica do sistema para uso em testes.
 * Agrupa os servicos e delega operacoes a eles.
 */
public class Facade {

    private final UsuarioService usuarioService;
    private final SessaoService sessaoService;
    private final RelacionamentoService relacionamentoService;
    private final RecadoService recadoService;
    private final MensagemService mensagemService;
    private final ComunidadeService comunidadeService;
    private final RemocaoUsuarioService remocaoUsuarioService;
    UsuarioRepository usuarioRepository;
    SessaoRepository sessaoRepository;
    ComunidadeRepository comunidadeRepository;
    ParticipacaoComunidadeRepository participacaoComunidadeRepository;

    /**
     * Inicializa a fachada com os repositórios e serviços usados pelo sistema.
     */
    public Facade() {
        this.usuarioRepository = new UsuarioRepository();
        this.sessaoRepository = new SessaoRepository();
        this.comunidadeRepository = new ComunidadeRepository();
        this.participacaoComunidadeRepository = new ParticipacaoComunidadeRepository();
        this.usuarioService = new UsuarioService(usuarioRepository, sessaoRepository);
        this.sessaoService = new SessaoService(usuarioRepository, sessaoRepository);
        this.relacionamentoService = new RelacionamentoService(usuarioRepository, sessaoRepository);
        this.recadoService = new RecadoService(usuarioRepository, sessaoRepository);
        this.mensagemService = new MensagemService(usuarioRepository, sessaoRepository, comunidadeRepository, participacaoComunidadeRepository);
        this.comunidadeService = new ComunidadeService(usuarioRepository, sessaoRepository, comunidadeRepository, participacaoComunidadeRepository);
        this.remocaoUsuarioService = new RemocaoUsuarioService(usuarioRepository, sessaoRepository, comunidadeRepository, participacaoComunidadeRepository);
    }

    // Sistema

    /**
     * Zera o sistema removendo todos os usuários em memória e excluindo
     * o arquivo de persistência.
     */
    public void zerarSistema(){
        usuarioRepository.limpar();
        usuarioRepository.apagarPersistencia();
        comunidadeRepository.limpar();
        comunidadeRepository.apagarPersistencia();
        participacaoComunidadeRepository.limpar();
        participacaoComunidadeRepository.apagarPersistencia();
        sessaoRepository.limpar();
    }

    /**
     * Salva o estado atual do repositório de usuários no disco.
     */
    public void encerrarSistema(){
        usuarioRepository.save();
        comunidadeRepository.save();
        participacaoComunidadeRepository.save();
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

    // Sessao

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

    /**
     * Edita um atributo do perfil do usuário associado à sessão informada.
     *
     * @param id id da sessão do usuário
     * @param atributo atributo que será criado ou alterado
     * @param valor novo valor do atributo
     * @throws UsuarioNaoCadastradoException se a sessão não existir
     */
    public void editarPerfil(String id, String atributo, String valor)
            throws UsuarioNaoCadastradoException {
        usuarioService.editarPerfil(id, atributo, valor);
    }

    /**
     * Remove o usuário associado à sessão informada.
     *
     * @param id id da sessão do usuário a remover
     * @throws UsuarioNaoCadastradoException se a sessão não existir ou o usuário não estiver cadastrado
     */
    public void removerUsuario(String id) throws UsuarioNaoCadastradoException {
        remocaoUsuarioService.removerUsuario(id);
    }

    //Amizades

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
     * @throws UsuarioNaoPodeSeAutoAdicionarException se o usuário tentar adicionar a si mesmo
     * @throws FuncaoInvalidaUsuarioInimigoException se o amigo marcou a origem como inimiga
     */
    public void adicionarAmigo(String id, String amigo)
            throws UsuarioNaoCadastradoException, UsuarioJaEstaAdicionadoComoAmigoException,
            EsperandoAceitacaoDoConviteException, UsuarioNaoPodeSeAutoAdicionarException,
            FuncaoInvalidaUsuarioInimigoException {
        relacionamentoService.adicionarAmigo(id, amigo);
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
        return relacionamentoService.ehAmigo(login, amigo);
    }

    /**
     * Retorna a lista de amigos do usuário no formato {a,b,c}
     *
     * @param login login do usuário
     * @return string contendo os amigos do usuário
     * @throws UsuarioNaoCadastradoException se o usuário não existir
     */
    public String getAmigos(String login) throws UsuarioNaoCadastradoException {
        return relacionamentoService.getAmigos(login);
    }

    // Relacionamentos

    /**
     * Adiciona um usuário como ídolo do usuário associado à sessão.
     *
     * @param id id da sessão do usuário fã
     * @param idolo login do usuário ídolo
     * @throws UsuarioNaoCadastradoException se a sessão ou o ídolo não existir
     * @throws FuncaoInvalidaUsuarioInimigoException se o ídolo marcou a origem como inimiga
     * @throws UsuarioJaEstaAdicionadoComoIdoloException se o ídolo já estiver adicionado
     * @throws UsuarioNaoPodeSerFaDeSiMesmoException se o usuário tentar ser fã de si mesmo
     */
    public void adicionarIdolo(String id, String idolo)
            throws UsuarioNaoCadastradoException, FuncaoInvalidaUsuarioInimigoException,
            UsuarioJaEstaAdicionadoComoIdoloException, UsuarioNaoPodeSerFaDeSiMesmoException {
        relacionamentoService.adicionarIdolo(id, idolo);
    }

    /**
     * Verifica se um usuário é fã do ídolo informado.
     *
     * @param login login do usuário fã
     * @param idolo login do usuário ídolo
     * @return true se o usuário for fã do ídolo
     * @throws UsuarioNaoCadastradoException se o usuário fã não existir
     */
    public boolean ehFa(String login, String idolo) throws UsuarioNaoCadastradoException {
        return relacionamentoService.ehFa(login, idolo);
    }

    /**
     * Retorna os fãs do usuário no formato esperado pela fachada.
     *
     * @param login login do usuário idolatrado
     * @return fãs no formato {login1,login2}
     * @throws UsuarioNaoCadastradoException se o usuário não existir
     */
    public String getFas(String login) throws UsuarioNaoCadastradoException {
        return relacionamentoService.getFas(login);
    }

    /**
     * Adiciona um usuário como paquera do usuário associado à sessão.
     *
     * @param id id da sessão do usuário de origem
     * @param paquera login do usuário paquera
     * @throws UsuarioNaoCadastradoException se a sessão ou a paquera não existir
     * @throws FuncaoInvalidaUsuarioInimigoException se a paquera marcou a origem como inimiga
     * @throws UsuarioJaEstaAdicionadoComoPaqueraException se a paquera já estiver adicionada
     * @throws UsuarioNaoPodeSerPaqueraDeSiMesmoException se o usuário tentar paquerar a si mesmo
     */
    public void adicionarPaquera(String id, String paquera)
            throws UsuarioNaoCadastradoException, FuncaoInvalidaUsuarioInimigoException,
            UsuarioJaEstaAdicionadoComoPaqueraException, UsuarioNaoPodeSerPaqueraDeSiMesmoException {
        relacionamentoService.adicionarPaquera(id, paquera);
    }

    /**
     * Verifica se a paquera informada pertence ao usuário associado à sessão.
     *
     * @param id id da sessão do usuário
     * @param paquera login da possível paquera
     * @return true se a paquera estiver adicionada
     * @throws UsuarioNaoCadastradoException se a sessão não existir
     */
    public boolean ehPaquera(String id, String paquera) throws UsuarioNaoCadastradoException {
        return relacionamentoService.ehPaquera(id, paquera);
    }

    /**
     * Retorna as paqueras do usuário associado à sessão.
     *
     * @param id id da sessão do usuário
     * @return paqueras no formato {login1,login2}
     * @throws UsuarioNaoCadastradoException se a sessão não existir
     */
    public String getPaqueras(String id) throws UsuarioNaoCadastradoException {
        return relacionamentoService.getPaqueras(id);
    }

    /**
     * Adiciona um usuário como inimigo do usuário associado à sessão.
     *
     * @param id id da sessão do usuário de origem
     * @param inimigo login do usuário inimigo
     * @throws UsuarioNaoCadastradoException se a sessão ou o inimigo não existir
     * @throws UsuarioJaEstaAdicionadoComoInimigoException se o inimigo já estiver adicionado
     * @throws UsuarioNaoPodeSerInimigoDeSiMesmoException se o usuário tentar ser inimigo de si mesmo
     */
    public void adicionarInimigo(String id, String inimigo)
            throws UsuarioNaoCadastradoException, UsuarioJaEstaAdicionadoComoInimigoException,
            UsuarioNaoPodeSerInimigoDeSiMesmoException {
        relacionamentoService.adicionarInimigo(id, inimigo);
    }
    // Recado

    /**
     * Encaminha um recado a partir da sessão `id` para `destinatario`.
     * @param id id da sessão do remetente
     * @param destinatario login do destinatário
     * @param mensagem texto do recado
     * @throws UsuarioNaoCadastradoException se a sessão for inválida
     * @throws UsuarioNaoPodeSeAutoEnviarMensagemException se tentar enviar recado para si
     * @throws FuncaoInvalidaUsuarioInimigoException se o destinatário marcou o remetente como inimigo
     */
    public void enviarRecado(String id , String destinatario, String mensagem)
            throws UsuarioNaoCadastradoException, UsuarioNaoPodeSeAutoEnviarMensagemException,
            FuncaoInvalidaUsuarioInimigoException {
        recadoService.enviarRecado(id, destinatario, mensagem);
    }

    /**
     * Lê o recado mais antigo recebido pelo usuário associado à sessão `id`.
     * @param id id da sessão do usuário
     * @return texto do recado lido
     * @throws UsuarioNaoCadastradoException se a sessão for inválida
     * @throws NaoHaRecadosException se não houver recados a serem lidos
     */
    public String lerRecado(String id)
            throws UsuarioNaoCadastradoException, NaoHaRecadosException {
        return  recadoService.lerRecado(id);
    }

    // Mensagem

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
        mensagemService.enviarMensagem(id, comunidade, mensagem);
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
        return mensagemService.lerMensagem(id);
    }

    // Comunidade

    /**
     * Cria uma comunidade para o usuário associado à sessão informada.
     *
     * @param id id da sessão do usuário dono
     * @param nome nome da comunidade
     * @param descricao descrição da comunidade
     * @throws UsuarioNaoCadastradoException se a sessão não existir
     * @throws ComunidadeJaExisteException se já existir comunidade com o mesmo nome
     */
    public void criarComunidade(String id, String nome, String descricao)
            throws UsuarioNaoCadastradoException, ComunidadeJaExisteException {
        comunidadeService.criarComunidade(id, nome, descricao);
    }

    /**
     * Retorna a descrição da comunidade informada.
     *
     * @param nome nome da comunidade
     * @return descrição da comunidade
     * @throws ComunidadeNaoExisteException se a comunidade não existir
     */
    public String getDescricaoComunidade(String nome) throws ComunidadeNaoExisteException {
        return comunidadeService.getDescricaoComunidade(nome);
    }

    /**
     * Retorna o login do dono da comunidade informada.
     *
     * @param nome nome da comunidade
     * @return login do dono da comunidade
     * @throws ComunidadeNaoExisteException se a comunidade não existir
     */
    public String getDonoComunidade(String nome) throws ComunidadeNaoExisteException {
        return comunidadeService.getDonoComunidade(nome);
    }

    /**
     * Retorna os membros da comunidade no formato esperado pela fachada.
     *
     * @param nome nome da comunidade
     * @return membros no formato {login1,login2}
     * @throws ComunidadeNaoExisteException se a comunidade não existir
     */
    public String getMembrosComunidade(String nome) throws ComunidadeNaoExisteException {
        return comunidadeService.getMembrosComunidade(nome);
    }

    /**
     * Retorna as comunidades das quais o usuário participa.
     *
     * @param login login do usuário
     * @return comunidades no formato {nome1,nome2}
     * @throws UsuarioNaoCadastradoException se o usuário não existir
     */
    public String getComunidades(String login) throws UsuarioNaoCadastradoException {
        return comunidadeService.getComunidades(login);
    }

    /**
     * Adiciona o usuário associado à sessão informada em uma comunidade.
     *
     * @param id id da sessão do usuário
     * @param nome nome da comunidade
     * @throws UsuarioNaoCadastradoException se a sessão não existir
     * @throws ComunidadeNaoExisteException se a comunidade não existir
     * @throws UsuarioJaFazParteDaComunidadeException se o usuário já participar da comunidade
     */
    public void adicionarComunidade(String id, String nome)
            throws UsuarioNaoCadastradoException, ComunidadeNaoExisteException,
            UsuarioJaFazParteDaComunidadeException {
        comunidadeService.adicionarComunidade(id, nome);
    }
}
