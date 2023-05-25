package tec.yam.livraria.domain.livro;

import tec.yam.livraria.domain.autor.Autor;

import java.time.Year;

public record DadosListagemLivro(
        String titulo,
        Genero genero,
        Year anoLancamento,
        Integer paginas,
        Autor autor
) {

    public DadosListagemLivro(Livro livro) {
        this(livro.getTitulo(), livro.getGenero(), livro.getAnoLancamento(), livro.getPaginas(), livro.getAutor());
    }
}
