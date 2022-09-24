package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Cotacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idC;
    private String nome;
    private String fornecedor;
    private float precoCotado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produtoFk")
    @JsonBackReference(value = "cotacoesProduto")
    private Produto produtoFk;

    public Cotacao(){
        
    }
    public Cotacao(int idC, String nome, String fornecedor, float precoCotado, Produto produtoFk) {
        this.idC = idC;
        this.nome = nome;
        this.fornecedor = fornecedor;
        this.precoCotado = precoCotado;
        this.produtoFk = produtoFk;
    }

    @JsonIgnore
    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    

    public float getPrecoCotado() {
        return precoCotado;
    }

    public void setPrecoCotado(float precoCotado) {
        this.precoCotado = precoCotado;
    }
    

}
