package tec.yam.livraria.domain.livro;

import tec.yam.livraria.domain.autor.Autor;

import java.time.Year;

public record DadosDetalhamentoLivro (Long id,
                                      String titulo,
                                      Genero genero,
                                      Year anoLancamento,
                                      Integer paginas,
                                      Autor autor)
{

    public DadosDetalhamentoLivro(Livro livro) {
        this(livro.getId(),
                livro.getTitulo(),
                livro.getGenero(),
                livro.getAnoLancamento(),
                livro.getPaginas(),
                livro.getAutor());
    }

}
