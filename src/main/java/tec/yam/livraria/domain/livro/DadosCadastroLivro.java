package tec.yam.livraria.domain.livro;

import tec.yam.livraria.domain.autor.DadosAutor;

import java.time.Year;

public record DadosCadastroLivro(
        String titulo,
        Genero genero,
        Year anoLancamento,
        Integer paginas,
        DadosAutor autor
) {
}
