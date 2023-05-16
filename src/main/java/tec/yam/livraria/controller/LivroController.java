package tec.yam.livraria.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tec.yam.livraria.domain.livro.DadosCadastroLivro;

@RestController
@RequestMapping("livros")
public class LivroController {

    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroLivro dados) {
        System.out.println(dados);
    }

}
