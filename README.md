<h1>API Livraria Tec</h1>
<p align="left">
  <img alt="GitHub status" src="http://img.shields.io/static/v1?label=STATUS&message=EmAndamento&color=GREEN&style=for-the-badge"/>
</p>

<p>Estou construindo esse projeto depois de concluir a Formação Java e Spring Boot da Alura. Meu objetivo é aplicar tudo o que aprendi. Mas, do que se trata essa aplicação? 
A API foi feita para servir uma livraria e permite o gerenciamento das suas atividades</p>

<h2>&#x1F528 Funcionalidades do projeto</h2>

<ul>
  <li>Cadastro de livros</li>
  <li>Listagem de livros</li>
  <li>Atualização de status dos livros</li>
  <li>Exclusão dos livros</li>
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
<p>O primeiro passo depois da configuração inicial foi implementar um sistema de cadastro de livros na API. Optei por usar o padrão DTO para representar os dados que chegam na API já que quero filtrar quais dados serão transmitidos. Fiz uso dos Records para setar os campos que desejo receber.</p>

<div align="center">
  <img src="img/cadastro.png" width="500px" heigth="500px"/>
  <p>Ainda não estamos trabalhando com banco de dados. Mas logo logo iremos fazê-lo :)</p>
</div>

<h4>1.1.1 - Conexão com banco de dados</h4>
<p>Para fazer a persistência foi necessário adicinar as dependências do Spring Data JPA e do banco de dados MySQL. Utiliei o padrão Repository para lidar com os dados. Assim não precisei me preocupar em utilizar diretamente a API da JPA. Uma ferramenta muito interessante que descobri há pouco tempo foi o Flyway. Ele me permite fazer as Migrations, que são arquivos criados dentro do projeto onde escrevemos comandos SQL para serem executados no banco de dados.</p>

<div align="center">
  <img alt="Imagem de exemplo - Persistência" src="" width="500px" heigth="500px"/>
  <p></p>
</div>

<h4>1.1.2 - Validações</h4>
<p>Fazer as validações se tornou bastante simples com o uso do Bean Validation a partir de anotações. Ele verifica se as informações que chegam estão de acordo com as anotações.</p>

<div align="center">
  <img alt="Imagem de exemplo - Validações" src="" width="500px" heigth="500px"/>
  <p></p>
</div>

<h4>1.2 - Listagem</h4>
<p>A primeira ideia no método responsável por listar os livros cadastrados foi que ele retornasse um List de livros. Porém, resolvi mais uma vez utilizar o padrão DTO. Já que eu não queria utilizar todos os dados da entidade Livro, optei por criar um DTO que traz apenas os dados desejados. Nesse sentido, vale destacar mais uma vez a flexibilidade que o padrão DTO proporciona. Outra decisão importante foi a de retornar Page em vez de List. Fazer isso me ajudou a ter um maior controle sobre questôes referentes à paginação. Agora eu posso definir, por exemplo, a ordem padrão em que os dados serão exibidos.</p>

<div align="center">
  <img alt="Imagem de exemplo - Listagem" src="" width="500px" heigth="500px"/>
  <p></p>
</div>

<h4>1.3 - Atualização de dados</h4>
<p>Criar a funcionalidade de atualizar os dados dos livros foi bastante simples. Primeiro tive que decidir quais dados dos livros seriam "atualizáveis". Ao longo do tempo alguns livros vão recebendo novas edições. Isso geralmente afeta o título do livro (que recebe o número da nova edição), o número de páginas e o ano de lançamento (que passa a ser o ano em que determinada edição foi lançada). Já que eu não quero deixar todos os atributos de um livro passíveis de receber atualização, criei um DTO que recebe apenas os dados mencionados acima, além do ID para identificar qual livro vai sofrer a atualização.</p>

<div align="center">
  <img alt="Imagem de exemplo - Atualização" src="" width="500px" heigth="500px"/>
  <p></p>
</div>

<h4>1.4 - Excusão lógica</h4>
<p>Antes mesmo de implementar a funcionalidade de exclusão, tive que escolher o tipo de exclusão que faria mais sentido para o negócio. Exclusão  tradicional? Isso significa excluir o dado (no caso o livro) do banco de dados. Acredito que para o cliente isso não seria tão interessante. Ele poderia fazer a exclusão do livro quando o mesmo não estivese mais disponível na livraria. Mas e se depois de algum tempo esse livro voltasse a fazer parte do estoque? O cliente teria que adicionar os dados novamente no banco? Dá para evitar esse tipo de trabalho! A solução se chama exclusão lógica. Nela, em vez de exluir o dado do banco, apenas tornamos ele inativo. Para isso, tive que adicionar uma coluna na tabela de livros que basicamente me diz se o livro está disponível ou não. Além disso foi necessário alterar o método que lista os livros para que ele apenas exibisse os livros disponíveis naquele momento.</p>

<div align="center">
  <img alt="Imagem de exemplo - Exclusão" src="" width="500px" heigth="500px"/>
  <p></p>
</div>

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
<p>Para colocar uma camada de segurança na minha API comecei adicionando um módulo do Spring Security no projeto. A partir de agora, qualquer requisição que eu fizer à API irá retornar o código HTTP 401 (Unauthorized). Vamos começar as nossas implementações: criei a entidade usuário; utilizei migrations para criar uma nova tabela no banco de dados onde serão armazenados os usuários e suas respectivas senhas; criei um repository do usuário; criei uma classe que terá o código com a lógica de autenticação; criei uma classe para configurar o Spring Security. Nessa classe eu desabilito a proteção contra ataques do tipo CSFR. Por que? Porque vou trabalhar com autenticação via tokens. Nesse cenário, o próprio token é uma proteção contra esses tipos de ataques. Além disso, eu desabilito o processo de autenticação padrão do Spring que é Stateful. Uma API Rest precisa ser Stateless.</p>

<div align="center">
  <img alt="Imagem de exemplo - Spring Security" src="" width="500px" heigth="500px"/>
  <p></p>
</div>


<h2>&#x1F4C1 Acesso ao projeto</h2>
<p>Você pode <a href="https://github.com/Yam-BS/api-livraria-tec/tree/master/src">acessar o código-fonte do projeto</a> ou <a href="https://github.com/Yam-BS/api-livraria-tec/archive/refs/heads/master.zip">baixá-lo</a></p>
