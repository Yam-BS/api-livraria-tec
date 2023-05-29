package tec.yam.livraria.domain.livro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tec.yam.livraria.domain.autor.Autor;

import java.time.Year;

@Table(name = "livros")
@Entity (name = "Livro")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Livro {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Genero genero;
    private Year anoLancamento;
    private Integer paginas;

    @Embedded
    private Autor autor;

    private Boolean disponivel;

    public Livro(DadosCadastroLivro dados) {
        this.titulo = dados.titulo();
        this.genero = dados.genero();
        this.anoLancamento = dados.anoLancamento();
        this.paginas = dados.paginas();
        this.autor = new Autor(dados.autor());
        this.disponivel = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoLivro dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }

        if (dados.anoLancamento() != null) {
            this.anoLancamento = dados.anoLancamento();
        }

        if(dados.paginas() != null) {
            this.paginas = dados.paginas();
        }
    }

    public void excluir() {
        this.disponivel = false;
    }
}
