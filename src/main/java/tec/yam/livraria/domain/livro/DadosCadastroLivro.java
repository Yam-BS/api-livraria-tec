package tec.yam.livraria.domain.livro;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tec.yam.livraria.domain.autor.DadosAutor;

import java.time.Year;

public record DadosCadastroLivro(
        @NotBlank(message = "{titulo.obrigatorio}")
        String titulo,

        @NotNull(message = "{genero.obrigatorio}")
        Genero genero,

        @NotNull(message = "{anoLancamento.obrigatorio}")
        Year anoLancamento,

        @NotNull(message = "{paginas.obrigatorio}")
        Integer paginas,

        @NotNull
        @Valid
        DadosAutor autor
) {
}
