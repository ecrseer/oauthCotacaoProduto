package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;

    private String password;
    private String username;
    private Boolean enabled = true;
    private Boolean credentialsNonExpired = true;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    // "USER, ADMIN, OTHER"
    private String authorities = "USER";

    public Usuario(String nome, String email, String password, String username) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        // this.username = username;
        this.username = email;
    }
    public Usuario(long id, String nome, String email, String senha, String perfil, String password, String username) {
        this(nome, email, password, username);
        this.id = id;
    }
    public Usuario() {
    }

    public long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(long id) {
        this.id = id;
    }
   
    @JsonIgnore @Override
    public Collection<? extends GrantedAuthority> 
        getAuthorities() {
        // ["USER", "ADMIN", "OTHER"]
        var papeis = authorities.split(",");
        var papeisStrm = Arrays.stream(papeis);
        var papeisMap = papeisStrm.map(SimpleGrantedAuthority::new);
        var papeisList = papeisMap.toList();
        return papeisList;
        /* List<SimpleGrantedAuthority> retorno = new ArrayList<>();
        for (String papel : papeis){
            var sga = new SimpleGrantedAuthority(papel);
            retorno.add(sga);
        }
        return retorno; */
    }
    @Override
    public String getPassword() {
        return this.password;
    } 
    @Override
    public String getUsername() {
        return this.username;
        // return this.email;
    }
    @JsonIgnore @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }
    @JsonIgnore @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }
    @JsonIgnore @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
    @JsonIgnore @Override
    public boolean isEnabled() {
        // return isEnabled();
        return this.enabled;
    }
   
}
