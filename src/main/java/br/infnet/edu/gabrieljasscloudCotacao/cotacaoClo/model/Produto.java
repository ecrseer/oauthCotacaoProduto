package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idP;
    private String nome;
    private String marca;

    @OneToMany(mappedBy = "produtoFk", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference(value = "cotacoesProduto")
    private List<Cotacao> cotacoes;
    
    public Produto(){}
    public Produto(long id, String nome, String marca) {
        this.setIdP(id);
        this.nome = nome;
        this.marca = marca;
        cotacoes = new ArrayList<>();
    }
    public Produto(String nome, String marca){
        this.setNome(nome);
        this.setMarca(marca);
    }


    public Produto(int id, String nome, String marca, List<Cotacao> cotacoes) {
        this.idP = id;
        this.nome = nome;
        this.cotacoes=cotacoes;
        
    }


    public List<Cotacao> getCotacoes() {
        return cotacoes;
    }
    public void setCotacoes(List<Cotacao> cotacoes) {
        this.cotacoes = cotacoes;
    }
    public long getIdP() {
        return idP;
    }
    public void setIdP(long id) {
        this.idP = id;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    @Override
    public String toString() {
        return "Produto [id=" + idP + ", marca=" + marca + ", nome=" + nome + "]";
    }
}
