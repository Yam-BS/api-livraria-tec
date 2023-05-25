package tec.yam.livraria.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import tec.yam.livraria.domain.livro.DadosCadastroLivro;
import tec.yam.livraria.domain.livro.DadosListagemLivro;
import tec.yam.livraria.domain.livro.Livro;
import tec.yam.livraria.domain.livro.LivroRepository;

import java.util.List;

@RestController
@RequestMapping("livros")
public class LivroController {

    @Autowired
    private LivroRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroLivro dados) {
        Livro livro = new Livro(dados);
        repository.save(livro);
    }

    @GetMapping
    public Page<DadosListagemLivro> listar(@PageableDefault(sort = {"titulo"}) Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemLivro::new);
    }

}
