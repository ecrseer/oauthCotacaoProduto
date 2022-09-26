package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.service;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectResult;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.dtos.ProdutoComImagem;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Produto;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Usuario;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.repository.ProdutoRepository;

@Service
public class ProdutoService {
    final String DEFAULT_BUCKET = "gabecrbuck";

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    AmazonService amazonService;

    // Listar
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }
    
    public Produto exibir(Long id) {
        return produtoRepository.findByIdP(id);
    }
    private PutObjectResult salvaImagemProduto(Produto produto,MultipartFile multipartFile){
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
    public Produto salvarComImagem(Produto produtoSemImagem,MultipartFile imagemDoProduto) {
        //Produto produtoSemImagem = produto.makeClone();

        try {
            salvaImagemProduto(produtoSemImagem, imagemDoProduto);
            return produtoRepository.save(produtoSemImagem);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;

    }

    public Produto salvar(Produto produtoSemImagem) {
        //Produto produtoSemImagem = produto.makeClone();

        try {
            
            return produtoRepository.save(produtoSemImagem);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;

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
