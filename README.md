<h1>API Livraria Tec</h1>
<p align="left">
  <img alt="GitHub status" src="http://img.shields.io/static/v1?label=STATUS&message=EmAndamento&color=GREEN&style=for-the-badge"/>
</p>

<p>Estou construindo esse projeto depois de concluir a Formação Java e Spring Boot da Alura. Meu objetivo é aplicar tudo o que aprendi. Mas, do que se trata essa aplicação? 
A API foi feita para servir uma livraria. A lista de funcionalidades está logo abaixo</p>

<h2>&#x1F528 Funcionalidades do projeto</h2>

<ul>
  <li>Cadastro de livros</li>
  <li>Listagem de livros</li>
  <li>Atualização de status dos livros</li>
  <li>Exclusão dos livros</li>
  <li>Cadastro de usuário</li>
</ul>

<h2>&#x2705 Técnicas e tecnologias utilizadas</h2>

<ul>
  <li><i>Java 17</i></li>
  <li><i>Spring Initializr</i></li>
  <li><i>Spring Boot</i></li>
  <li><i>Spring Boot DevTools</i></li>
  <li><i>Lombok</i></li>
  <li><i>Spring Web</i></li>
  <li><i>Insomnia</i></li>
  <li><i>Spring Data JPA</i></li>
  <li><i>MySQL</i></li>
  <li><i>Flyway</i></li>
  <li><i>Spring Security</i></li>
</ul>

<h2>&#x1F477;&#x1F3FE; Construção do projeto passo a passo</h2>

<h3>1 - CRUD da API RESTful</h3>

<h4>1.1 - Cadastro</h4>
<p>O primeiro passo depois da configuração inicial foi implementar a funcionalidade de cadastro de livros na API. Optei por usar o padrão DTO para representar os dados que chegam na API já que quero filtrar quais dados serão transmitidos. Fiz uso dos Records para setar os campos que desejo receber.</p>

```
//Método acionado em requisições do tipo POST responsável por fazer o cadastro de livros

@PostMapping
public void cadastrar(@RequestBody DadosCadastroLivro dados) {
  Livro livro = new Livro(dados);
  repository.save(livro);
}
```

<h4>1.1.1 - Conexão com banco de dados</h4>
<p>Para fazer a persistência foi necessário adicionar as dependências do Spring Data JPA e do banco de dados MySQL ao meu projeto. Utilizei o padrão Repository para lidar com os dados. Assim não precisei me preocupar em utilizar diretamente a API da JPA.

```
//Interface seguindo o padrão Repository, onde faço a persistência de livros no banco de dados

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
```
  
Uma ferramenta muito interessante que descobri há pouco tempo foi o Flyway. Ele me permite fazer as Migrations, que são arquivos criados dentro do projeto onde escrevemos comandos SQL para serem executados no banco de dados.</p>

```
//Exemplo de migration, onde crio a tabela "livros" no banco de dados

create table livros(

    id bigint not null auto_increment,
    titulo varchar(100) not null,

    //código omitido...

    primary key(id)

);
```

<h4>1.1.2 - Validações</h4>
<p>Fazer as validações dos dados que chegam foi bastante simples. Usei o Bean Validation para fazer isso através de anotações. Ele verifica se as informações que chegam estão de acordo com as anotações.</p>

```
//Observe as anotações acima dos atributos. A anotação @NotNull, por exemplo, não permite que o campo venha nulo

public record DadosCadastroLivro(
        @NotBlank
        String titulo,
        @NotNull
        Genero genero,
        @NotNull
        Year anoLancamento,
        @NotNull
        Integer paginas,
        @NotNull
        @Valid
        DadosAutor autor
) {
}
```

<h4>1.2 - Listagem</h4>
<p>Vamos agora ao método responsável por listar os livros cadastrados. Minha primeira ideia foi que ele retornasse um <i>List</i> de livros. Mas o mais inteligente foi utilizar novamente o padrão DTO. Por quê? Bem, eu não queria que o método retornasse todos os dados da entidade <i>Livro</i>, e com o DTO eu tive controle para trazer apenas os dados desejados. Nesse sentido, vale destacar mais uma vez a flexibilidade que o padrão DTO proporciona.

Outra decisão importante foi a de retornar um <i>Page</i> com os dados dos livros em vez de <i>List</i>. Fazer isso me ajudou a definir um padrão sobre questôes referentes à paginação e ordenação. Pude definir, por exemplo, que por padrão os livros seriam exibidos em ordem alfabética.</p>

```
@GetMapping
public Page<DadosListagemLivro> listar(@PageableDefault(sort = {"titulo"}) Pageable paginacao) {
    return repository.findAll(paginacao).map(DadosListagemLivro::new);
}
```

<h4>1.3 - Atualização de dados</h4>
<p>Ao longo do tempo alguns livros vão recebendo novas edições. Isso geralmente afeta o título do livro (que recebe o número da nova edição), o número de páginas e o ano de lançamento (que passa a ser o ano em que determinada edição foi lançada). Decidi então que esses dados serão passíveis de receber atualização. Outros dados, como autor e gênero, não. Mais uma vez criei um DTO para receber apenas os dados "atualizáveis", além do ID para identificar qual livro vai sofrer a atualização.</p>

```
//A anotação @Transactional deixa explícito o contexto de transação

@PutMapping
@Transactional
public void atualizar(@RequestBody @Valid DadosAtualizacaoLivro dados) {
    Livro livro = repository.getReferenceById(dados.id());
    livro.atualizarInformacoes(dados);
}
```

```
//DTO que possui apenas os dados que podem sofrer atualização

public record DadosAtualizacaoLivro(
        @NotNull
        Long id,

        @NotBlank
        String titulo,

        @NotNull
        Year anoLancamento,

        Integer paginas
) {
}
```


<h4>1.4 - Excusão lógica</h4>
<p>Antes mesmo de implementar a funcionalidade de exclusão, tive que escolher o tipo de exclusão que faria mais sentido para o negócio.</p> 

<ul>
  <li>Exclusão tradicional: Consiste em excluir o dado (no caso o livro) do banco de dados</li>
  <li>Exclusão lógica: Em vez de exluir o dado do banco, apenas tornamos ele inativo</li>
</ul>

<p>Vamos pensar no cliente: Ele utilizará a ferramenta de exclusão do livro quando o mesmo não estiver mais disponível na livraria. Mas e se depois de algum tempo esse livro voltasse a fazer parte do estoque? Caso minha implementação seja a exclusão tradicional, o cliente terá que adicionar o livro novamente no banco, preenchendo cada um dos campos necessários.</p> 
  
<p>Para evitar que ele tenha todo esse trabalho, adicionei uma coluna na tabela de livros (e também um atributo na entidade) que indica se o livro está disponível ou não. Quando o cliente excluir um livro, ele estará apenas modificando esse campo.</p> 

```
//O cliente precisa passar o ID do livro a ser excluído

@DeleteMapping("/{id}")
@Transactional
public void excluir(@PathVariable Long id) {
    Livro livro = repository.getReferenceById(id);
    livro.excluir();
}
```
```
public class Livro(){
  //código omitido...
  private Boolean disponivel;

  public void excluir() {
      this.disponivel = false;
  }
}
```

<p>Além disso, alterei o método que lista os livros para fazê-lo exibir apenas os livros disponíveis naquele momento.</p>

```
//Utilizando um padrão de nomenclatura, criei um método que busca no banco apenas os livros que estão disponíveis

public Page<DadosListagemLivro> listar(@PageableDefault(sort = {"titulo"}) Pageable paginacao) {
    return repository.findAllByDisponivelTrue(paginacao).map(DadosListagemLivro::new);
}
```
```
//O método do código anterior precisa ser declarado na classe Repository

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Page<Livro> findAllByDisponivelTrue(Pageable paginacao);
}
```


<h3>2 - Aplicando boas práticas e protegendo a API</h3>

<h4>2.1 - Padronizando retornos</h4>
<p>O protocolo HTTP possui diversos códigos para vários cenários. Por isso fiz alguns ajustes para retornar os códigos mais adequados de acordo com a requisição. Usei a classe ResponseEntity do Spring que ajuda a controlar a resposta devolvida pelo Spring. Todos os meus métodos do controller devolvem agora um ResponseEntity. Essa classe possui alguns métodos estáticos que posso devolver conforme o que desejo.</p>

<div align="center">
  <img alt="Imagem de exemplo - ResponseEntity" src="" width="500px" heigth="500px"/>
  <p></p>
</div>

<h4>2.2 - Tratando erros</h4>
<p>O que fazer quando ocorre algum erro? Por exemplo, talvez um cliente desavisado faça a busca de determinado livro utilizando um ID inexistente. Em situações assim, o Spring por padrão devolve o código HTTP 500, além da stack trace informando qual exception ocorreu. Mas isso não é muito recomendado. 

Para que o Spring não envie a stack trace em caso de erro, adicionei a seguinte configuração no arquivo application.properties: <i>server.error.include-stacktrace=never</i>

E para capturar o erro? Adicionar try-catch ao método de exibir livro na classe controller não é muito interessante. Isso porque outros métodos de outros controllers podem lançar o mesmo tipo de erro. Ou seja, eu teria try-catch espalhado em vários controllers. A solução foi criar uma classe com métodos responsáveis por tratar erros específicos. Em cima do método eu coloco o tipo de exceção que ele irá tratar.
  
Então vamos lá: buscar um livro utilizando um ID que não existe no banco de dados irá lançar uma <i>EntityNotFoundException</i>. Sempre que isso acontecer, o Spring pede socorro ao método que sabe tratar essa exceção.
</p>

<div align="center">
  <img alt="Imagem de exemplo - Classe responsável pelo tratamento de erros" src="" width="500px" heigth="500px"/>
  <p></p>
</div>

<h4>2.3 - Trabalhando com o Spring Security</h4>
<p>Para colocar uma camada de segurança na minha API comecei adicionando um módulo do Spring Security no projeto. A partir de agora, qualquer requisição que eu fizer à API irá retornar o código HTTP 401 (Unauthorized). Vamos começar as nossas implementações: criei a entidade usuário; utilizei migrations para criar uma nova tabela no banco de dados onde serão armazenados os usuários e suas respectivas senhas; criei um repository do usuário; criei uma classe que terá o código com a lógica de autenticação; criei uma classe para configurar o Spring Security. Nessa classe eu desabilito a proteção contra ataques do tipo CSFR. Por quê? Porque vou trabalhar com autenticação via tokens. Nesse cenário, o próprio token é uma proteção contra esses tipos de ataques. Além disso, eu desabilito o processo de autenticação padrão do Spring que é Stateful. Uma API Rest precisa ser Stateless.

O próximo passo foi construir o controller responsável por disparar o processo de autenticação. Esse controller possui o método de efetuar login que recebe um DTO com os dados de autenticação. No nosso caso, login e senha. No controller também precisei usar a classe <i>AuthenticationManager</i>, do Spring. Essa classe possui o método <i>authenticate()</i> que recebe como parâmetro um objeto do tipo <i>UsernameAuthenticationToken</i> e devolve o objeto que representa o usuário autenticado no sistema. No fim, retornei o método HTTP 200 OK.
  
Agora vamos à classe <i>SecurityConfigurations</i>. Nela coloquei o método <i>authenticationManager()</i> anotado com <i>@Bean</i>. Foi necessário fazer isso porque o Spring não injeta de forma automática o objeto <i>AuthenticationManager</i>. O método criado informa ao Spring como fazer isso.
  
Mas como vamos salvar a senha do usuário no banco de dados? Não é uma boa prática salvar em texto aberto pois é um dado sensível. A boa prática é salvar a senha em hashing. Aqui usarei o formato BCrypt de hashing da senha. Como o Spring identifica que estamos usando o BCrypt? Utilizei o método abaixo para que isso fosse possível.
  
Está na hora de cuidar da classe usuário. Preciso que o Spring Security identifique essa classe. Que ele saiba, por exemplo, que o atributo login é o campo login. A forma de fazer isso é implementando a interface <i>UserDetails</i> e, por consequência, os seus métodos. Fiz algumas alterações nos métodos e pronto. A classe Usuário está seguindo o padrão do Spring.
</p>

<div align="center">
  <img alt="Imagem de exemplo - Spring Security" src="" width="500px" heigth="500px"/>
  <p></p>
</div>

<h4>2.4 - Gerando Tokens JWT</h4>
<p>Agora o nosso foco será ter o token no retorno da requisição. 

Antes de tudo, vamos adicionar a biblioteca Auth0 ao projeto. Ela será utilizada para gerar o token, seguindo o padrão JWT. Peguei essa biblioteca no <a href="https://jwt.io/">site do JWT</a>.

Criei a classe <i>TokenService</i> que irá fazer a geração, validação e o que mais estiver relacionado aos tokens. Já que a classe representa um serviço, passei a anotação <i>@Service</i>. Ela possui o método <i>gerarToken()</i> que irá retornar o token em formato de String. <a href="https://github.com/auth0/java-jwt">Neste repositório do JWT</a> encontramos a seção "Create a JWT" que possui um trecho de código Java que serve para gerar o token. Copiei e colei dentro do meu método trocando o algoritmo padrão de assinatura (RS256) pelo HMAC256. Como parâmetro passei uma senha, o que é indispensável para fazer a assinatura do token. A boa prática é que a senha esteja em uma variável de ambiente para que não fique exposta em código aberto.
  
O ideal é que os tokens da API tenham data de validade. Chamei o método <i>withExpiresAt()</i>, passando como parâmetro <i>dataExpiracao()</i> o que dá uma validade de duas horas ao token gerado.
  
Criei o DTO <i>DadosTokenJWT</i> para encapsular o token. Não quero que ele seja devolvido "solto" no corpo da resposta.
  
Vamos voltar à classe <i>AutenticacaoController</i>. Só relembrando: é aqui que chegam as requisições de login. Nela usei o DTO do Spring <i>UsernamePasswordAuthenticationToken</i>, passando o login e a senha que chegam e disparei o processo de autenticação. Após isso, passei o usuário autenticado como parâmetro para gerar o token. Com o token gerado, bastou retorná-lo no corpo da resposta. Ele veio dentro de um JSON graças ao meu DTO.
</p>

<div align="center">
  <img alt="Imagem de exemplo - Geração de tokens" src="" width="500px" heigth="500px"/>
  <p></p>
</div>

<h4>2.5 - Controlando o acesso</h4>
<p>Agora meu cliente precisa enviar o token junto das próximas requisições para ter acesso ao controller. Para fazer esse sistema funcionar usei o conceito de <i>Filters</i>. Eles entrarão em ação antes mesmo da execução do Spring. Lá é onde decidiremos se a requisição será interrompida ou, se for o caso, chamaremos ainda outro filter. A ideia é que a requisição passe pelo filter antes de cair no controller.

Criei a classe <i>SecurityFilter</i> que vai servir como filtro no projeto, para interceptar requisições. Essa classe possui a anotação <i>@Component</i> para que o Spring consiga carregá-la no projeto. Outra característica dessa classe é que ela estende <i>OncePerRequestFilter</i>, que por sua vez provê o método <i>doFilterInternal</i>. Isso porque eu quero que o filtro seja invocado apenas uma vez por requisição.

Mas, e agora? Como meu filter vai pegar o token que vem na requisição para fazer a validação? O token vem junto da requisição, no cabeçalho "Authorization". Ainda na classe <i>SecurityFilter</i>, criei o método <i>recuperarToken</i> que vai buscar esse cabeçalho e pegar o token. Caso esse campo venha nulo, uma exceção será lançada. Caso contrário, o token irá passar por um tratamento: por padrão ele virá com a palavra "Bearer" na frente. O método <i>replace</i> da classe <i>String</i> me ajudou a removê-la. O retorno do meu método vai ser um token "limpo".

Está na hora de validar o token. Na classe <i>TokenService</i> criei o método <i>getSubject</i> que receberá o token. Voltando ao <a href="https://github.com/auth0/java-jwt">repositório do JWT</a>, podemos navegar até a seção "Verify a JWT" onde encontramos um código pronto de validação de token. Copiei e colei esse código dentro do meu método, fazendo algumas adaptações ao meu projeto. Esse método será chamado na classe <i>SecurityFilter</i> para que a validação seja realizada.

Precisei fazer algumas adaptações para que o Spring controle a liberação ou não das requisições. Primeiro alterei o método <i>securityFilterChain</i> da classe <i>SecurityConfigurations</i> para que o Spring saiba que a única requisição que deve ser liberada é a de login. Todas as requisições que não forem de login não passarão pelo filtro. Nesse ponto, qualquer requisição será bloqueada mesmo que eu envie junto o token. Isso acontece porque eu ainda preciso falar para o Spring que o token que está válido. Como?

Primeiro foi necessário carregar o usuário do banco de dados. Para falar ao Spring considerar que esse usuário está válido, criei uma espécie de DTO que faz parte do próprio Spring passando os dados desse usuário. Passei esse DTO no método que seta o usuário logado pertencente à classe <i>SecurityContextHolder</i>.

Por fim, um último ajuste. Por padrão o Spring possui um filtro que é chamado antes do meu, o que está fazendo algumas requisições ainda cairem no código HTTP 403. Assim, precisei modificar a ordem dos filtros para que o Spring chame primeiro o que configurei, verificando se o token está vindo e autenticando o usuário. Essa alteração foi feita na classe <i>SecurityConfigurations</i>, onde adicionei o método <i>addFilterBefore</i>

Ufa! Finalmente! A funcionalidade de autenticação e autorização via tokens está completa!

</p>

<div align="center">
  <img alt="Imagem de exemplo - SecurityFilter" src="" width="500px" heigth="500px"/>
  <p></p>
</div>


<h2>&#x1F4C1 Acesso ao projeto</h2>
<p>Você pode <a href="https://github.com/Yam-BS/api-livraria-tec/tree/master/src">acessar o código-fonte do projeto</a> ou <a href="https://github.com/Yam-BS/api-livraria-tec/archive/refs/heads/master.zip">baixá-lo</a></p>
