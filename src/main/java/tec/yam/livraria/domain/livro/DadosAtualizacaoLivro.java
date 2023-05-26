package tec.yam.livraria.domain.livro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Year;

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
