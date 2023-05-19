create table livros(

    id bigint not null auto_increment,
    titulo varchar(100) not null,
    genero varchar(100) not null,
    ano_lancamento int not null,
    paginas int not null,
    nome varchar(100) not null,
    livros_publicados int not null,

    primary key(id)

);