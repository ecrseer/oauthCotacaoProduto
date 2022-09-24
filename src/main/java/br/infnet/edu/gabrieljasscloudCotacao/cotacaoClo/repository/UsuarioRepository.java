package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Usuario;

@Repository
public interface UsuarioRepository 
    extends JpaRepository<Usuario, Long> {
        Usuario findByUsername(String username);
        Usuario findByEmail(String email);
}