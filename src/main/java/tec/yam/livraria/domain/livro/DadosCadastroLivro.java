package tec.yam.livraria.domain.livro;

import tec.yam.livraria.domain.autor.DadosAutor;

public record DadosCadastroLivro(
        String titulo,
        Genero genero,
        Integer anoLancamento,
        Integer paginas,
        DadosAutor autor
) {
}
