package tec.yam.livraria.domain.autor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAutor(
        @NotBlank
        String nome,
        @NotNull
        Integer livrosPublicados
) {
}
