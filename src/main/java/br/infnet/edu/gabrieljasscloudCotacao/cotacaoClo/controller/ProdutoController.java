package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Produto;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping
    public ResponseEntity listar() {
        var produtos = produtoService.listar();
        if (produtos.isEmpty())
            return ResponseEntity.ok().body("Lista vazia.");
        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity exibir(@PathVariable long id) {
        try {
            var produto = produtoService.exibir(id);
            return ResponseEntity.ok().body(produto);
        } catch (Exception e) {
            return ResponseEntity
                    .status(404)
                    .body("Nenhum produto encontrado.");
        }

    }

    @PostMapping
    public ResponseEntity inserir(@RequestBody Produto produto) {
        if (produto.getNome() == null || 
                produto.getMarca() == null)
            return ResponseEntity.status(400)
            .body("Dados incorretos.");

        produto = produtoService.salvar(produto);
        if (produto.getId() != 0)
            return ResponseEntity.status(201).body(produto);

        return ResponseEntity.status(500).body("Erro interno.");
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        if (produto.getNome() == null || 
                produto.getMarca() == null)
                    return ResponseEntity.status(400)
                        .body("Dados incorretos.");
        produto.setId(id);
        produto = produtoService.salvar(produto);
        return ResponseEntity.status(201).body(produto);
    }

    /* @PatchMapping("/{id}")
    public ResponseEntity alterar(@PathVariable Long id, @RequestBody Produto produto) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                var nome = produto.getNome();
                p.setNome(nome);
                var marca = produto.getMarca();
                p.setMarca(marca);
                return ResponseEntity.ok().body(p);
            }
        }
        return ResponseEntity.status(500).body("Erro interno.");
    }
 */
    
    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.ok().body("Produto excluido.");
    }
}
