package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Produto;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    // Listar
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }
    
    public Produto exibir(Long id) {
        return produtoRepository.findByIdP(id);
    }
    
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
    

    
    // atualizar
    public Produto atualizar(Produto produto) {
        return this.salvar(produto);
    }
    // excluir
    public void excluir(Long id) {
        produtoRepository.deleteById(id);
    }
    
}
