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
</ul>

<h2>&#x1F477;&#x1F3FE; Construção do projeto passo a passo</h2>

<h3>1 - CRUD da API RESTful</h3>

<h4>1.1 - Cadastro</h4>
<p>O primeiro passo depois da configuração inicial foi implementar um sistema de cadastro de livros na API. Optei por usar o padrão DTO para representar os dados que chegam na API já que quero filtrar quais dados serão transmitidos. Fiz uso dos Records para setar os campos que desejo receber.</p>

<div align="center">
  <img src="img/cadastro.png" width="500px" heigth="500px"/>
  <p>Ainda não estamos trabalhando com banco de dados. Mas logo logo iremos fazê-lo :)</p>
</div>

<h4>1.1.1 - Conectando ao banco de dados</h4>
<p>Para fazer a persistência foi necessário adicinar as dependências do Spring Data JPA e do banco de dados MySQL. Utiliei o padrão Repository para lidar com os dados. Assim não precisei me preocupar em utilizar diretamente a API da JPA. Uma ferramenta muito interessante que descobri há pouco tempo foi o Flyway. Ele me permite fazer as Migrations, que são arquivos criados dentro do projeto onde escrevemos comandos SQL para serem executados no banco de dados.</p>

<div align="center">
  <img alt="Imagem de exemplo - Persistência" width="500px" heigth="500px"/>
  <p></p>
</div>

<h4>1.1.2 - Fazendo validações</h4>
<p>Fazer as validações se tornou bastante simples com o uso do Bean Validation a partir de anotações. Ele verifica se as informações que chegam estão de acordo com as anotações.</p>

<div align="center">
  <img alt="Imagem de exemplo - Validações" src="" width="500px" heigth="500px"/>
  <p></p>
</div>



<h2>&#x1F4C1 Acesso ao projeto</h2>
<p>Você pode <a href="https://github.com/Yam-BS/api-livraria-tec/tree/master/src">acessar o código-fonte do projeto</a> ou <a href="https://github.com/Yam-BS/api-livraria-tec/archive/refs/heads/master.zip">baixá-lo</a></p>
