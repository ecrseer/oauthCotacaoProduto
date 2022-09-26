package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.dtos;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Cotacao;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Produto;

public class ProdutoComImagem extends Produto{
    public MultipartFile imagem;

    public ProdutoComImagem(long id, String nome, String marca, List<Cotacao> cotacoes, MultipartFile imagem) {
        super(id, nome, marca, cotacoes);
        this.imagem = imagem;
    }

    public ProdutoComImagem(MultipartFile imagem) {
        this.imagem = imagem;
    }

    public ProdutoComImagem(long id, String nome, String marca, MultipartFile imagem) {
        super(id, nome, marca);
        this.imagem = imagem;
    }

    public ProdutoComImagem(String nome, String marca, MultipartFile imagem) {
        super(nome, marca);
        this.imagem = imagem;
    }

    public MultipartFile getImagem() {
        return imagem;
    }

    public void setImagem(MultipartFile imagem) {
        this.imagem = imagem;
    }

    
}
