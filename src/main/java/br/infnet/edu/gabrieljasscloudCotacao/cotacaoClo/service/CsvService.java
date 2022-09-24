package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.service;

import org.springframework.stereotype.Service;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Cotacao;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Produto;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.util.Formatter;
import java.util.List;

@Service
public class CsvService {
    public void ProdutoToCsv(Produto p) {
        String rootDir = System.getProperty("user.dir");

        String baseUri = "src.main.java.br.edu.infnet.gabrieljcloudprodutocotacaoat.gjprodutcot";
        String separator = FileSystems.getDefault().getSeparator();
        String uri = rootDir + separator + baseUri.replaceAll("\\.", separator) + separator + p.getNome() + ".csv";
        System.out.println(uri);
        File arquivo = new File(uri);

        Formatter output = null;
        try {
            output = new Formatter(arquivo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        output.format("%s , %s , \n", "IdP", "Nome");
        output.format("%d , %s , \n\n", p.getIdP(), p.getNome());
        List<Cotacao> pCotacoes = p.getCotacoes();
        output.format("%s , %s , %s, %s \n", "IdC", "Nome", "Fornecedor", "Preco cotado");

        if (pCotacoes != null) {
            for (Cotacao c : p.getCotacoes()) {

                output.format("%d , %s , %s, %.2f \n", c.getIdC(), c.getNome(), c.getFornecedor(), c.getPrecoCotado());

            }

        }
        output.close();

    }
}
