package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.service;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectResult;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.dtos.LocalFileAndAWSKey;
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

    private LocalFileAndAWSKey getFileKeyFromProduto(Produto produto,String extension){
        if(extension == null) extension = "png";

        String newName = "/produtoImagem." + extension;
        String fileKeyAWS = "produtos/" + produto.getIdP() + "" + newName;

        String localPath = "src/main/resources/static/images/"+ produto.getIdP() + newName;
        File localFile = new File(localPath);
        
        return new LocalFileAndAWSKey(localFile, fileKeyAWS);
    }

    private PutObjectResult salvaImagemProduto(Produto produto, MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(filename);
        
        LocalFileAndAWSKey localFileAndAWSKey = getFileKeyFromProduto(produto, extension);        
        System.out.println("localPath:::" + localFileAndAWSKey.getLocalFile().getAbsolutePath());

        File filePointer = amazonService.convertMultiPartToFile(multipartFile, localFileAndAWSKey.getLocalFile());
        return amazonService.uploadSetFile(DEFAULT_BUCKET, localFileAndAWSKey.getFileKeyOnAWS(), filePointer);
    }

    public Produto salvaProdutoComImagem(Produto produtoSemImagem, MultipartFile imagemDoProduto) {
        try {
            Produto produtoSalvo = produtoRepository.save(produtoSemImagem);
            salvaImagemProduto(produtoSalvo, imagemDoProduto);
            return produtoSalvo;
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

    public File getProdutoImage(long produtoId){

        Produto produto = produtoRepository.findByIdP(produtoId);
        if(produto==null) return null;

        LocalFileAndAWSKey localFileAndAWSKey = getFileKeyFromProduto(produto,null);
        File arquivoLocal = localFileAndAWSKey.getLocalFile();

        try {
            if(!arquivoLocal.exists()){
                amazonService.getFileFrom(DEFAULT_BUCKET,
                 localFileAndAWSKey.getFileKeyOnAWS(),
                 arquivoLocal);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return arquivoLocal;
    }

    public Produto atualizar(Produto produto) {
        return this.salvaProduto(produto);
    }

    public void excluir(Long id) {
        Produto produto = produtoRepository.findByIdP(id);
        LocalFileAndAWSKey localFileAndAWSKey = getFileKeyFromProduto(produto, null);
        amazonService.removeFile(DEFAULT_BUCKET, localFileAndAWSKey.getFileKeyOnAWS());
        produtoRepository.deleteById(id);

    }

}
