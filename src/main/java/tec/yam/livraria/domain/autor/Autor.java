package tec.yam.livraria.domain.autor;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Autor {

    private String nome;
    private Integer livrosPublicados;

    public Autor(DadosAutor dados) {
        this.nome = dados.nome();
        this.livrosPublicados = dados.livrosPublicados();
    }
}
