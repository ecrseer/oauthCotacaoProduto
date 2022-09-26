package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.dtos.ProdutoComImagem;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Produto;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.service.CsvService;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    CsvService csvService;

    @GetMapping
    public ResponseEntity listar() {
        var produtos = produtoService.listar();
        if (produtos.isEmpty())
            return ResponseEntity.ok().body("Lista vazia.");
        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping("/{idP}")
    public ResponseEntity exibirUm(@PathVariable long idP) {
        try {
            var produto = produtoService.exibir(idP);            
            return ResponseEntity.ok().body(produto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(404)
                    .body("Nenhum produto encontrado.");
        }

    }

    @GetMapping("/gerarCsv/{idP}")
    public ResponseEntity gerarCsv(@PathVariable long idP) {
        try {
            var produto = produtoService.exibir(idP);
            csvService.ProdutoToCsv(produto);
            return ResponseEntity.ok().body(produto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(404)
                    .body("Nenhum produto encontrado.");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.ok().body("Produto excluido.");
    }

    @RequestMapping(path = "", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity inserirProdutoComImagem(@RequestPart MultipartFile imagem,@RequestPart Produto produto) {
        
        
        if (produto.getNome() == null ||
                produto.getMarca() == null)
            return ResponseEntity.status(400)
                    .body("Dados incorretos.");

        var produtoSem = produtoService.salvarComImagem(produto,imagem);
        if (produtoSem.getIdP() != 0)
            return ResponseEntity.status(201).body(produto);

        return ResponseEntity.status(500).body("Erro interno.");
    }

    @PutMapping("/{idP}")
    public ResponseEntity atualizar(@PathVariable Long idP, @RequestBody Produto produto) {
        if (produto.getNome() == null ||
                produto.getMarca() == null)
            return ResponseEntity.status(400)
                    .body("Dados incorretos.");
        produto.setIdP(idP);
        produto = produtoService.salvar(produto);
        return ResponseEntity.status(201).body(produto);
    }
 

   
}
