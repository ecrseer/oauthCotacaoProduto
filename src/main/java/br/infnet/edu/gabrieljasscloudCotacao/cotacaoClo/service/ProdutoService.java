package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.service;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectResult;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.dtos.ProdutoComImagem;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Cotacao;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Produto;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Usuario;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.repository.CotacaoRepository;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.repository.ProdutoRepository;

@Service
public class ProdutoService {
    final String DEFAULT_BUCKET = "gabecrbuck";

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CotacaoRepository cotacaoRepository;

    @Autowired
    AmazonService amazonService;

    // Listar
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Produto exibir(Long id) {
        return produtoRepository.findByIdP(id);
    }

    private PutObjectResult salvaImagemProduto(Produto produto, MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(filename);
        String newName = "/produtoImagem." + extension;

        String fileKeyAWS = "produtos/" + produto.getIdP() + "" + newName;

        String localPath = "src/main/resources/static/images/"
                + produto.getIdP() + newName;

        System.out.println("localPath:::" + localPath);
        File filePointer = amazonService.convertMultiPartToFile(multipartFile, new File(localPath));
        return amazonService.uploadSetFile(DEFAULT_BUCKET, fileKeyAWS, filePointer);
    }

    public Produto salvaProdutoComImagem(Produto produtoSemImagem, MultipartFile imagemDoProduto) {
        try {
            salvaImagemProduto(produtoSemImagem, imagemDoProduto);
            return produtoRepository.save(produtoSemImagem);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;

    }

    public Produto salvaProduto(Produto produtoSemImagem) {
        try {
            return produtoRepository.save(produtoSemImagem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public Cotacao salvaCotacao(Cotacao cotacao) {
        Cotacao salva = null;
        try {
            salva = cotacaoRepository.save(cotacao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salva;

    }

    public Produto atualizar(Produto produto) {
        return this.salvaProduto(produto);
    }

    public void excluir(Long id) {
        produtoRepository.deleteById(id);
    }

}
