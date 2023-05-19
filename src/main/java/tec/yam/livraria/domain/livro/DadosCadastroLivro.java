package tec.yam.livraria.domain.livro;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tec.yam.livraria.domain.autor.DadosAutor;

import java.time.Year;

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
