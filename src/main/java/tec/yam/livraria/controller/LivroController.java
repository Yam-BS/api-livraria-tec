package tec.yam.livraria.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tec.yam.livraria.domain.livro.DadosCadastroLivro;
import tec.yam.livraria.domain.livro.Livro;
import tec.yam.livraria.domain.livro.LivroRepository;

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

}
