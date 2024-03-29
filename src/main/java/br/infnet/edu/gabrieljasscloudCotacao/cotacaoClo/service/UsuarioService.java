package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Usuario;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.repository.UsuarioRepository;

@Service
public class UsuarioService 
    implements UserDetailsService {

    @Autowired
    UsuarioRepository usuariosRepository;

    
    public List<Usuario> listar() {
        return usuariosRepository.findAll();
    }
    
    public Usuario salvar(Usuario usuario) {
        BCryptPasswordEncoder encoder = 
                new BCryptPasswordEncoder();
        String password = usuario.getPassword();
        String bcryptPassword = encoder.encode(password);
        usuario.setPassword(bcryptPassword);
        return usuariosRepository.save(usuario);
    } 

    public Usuario exibir(Long id) {
        return usuariosRepository.findById(id).get();
    }
    
    public Usuario atualizar(Usuario usuario) {
        return this.salvar(usuario);
    }
     public boolean excluir(Long id) {
        
        try {
            usuariosRepository.deleteById(id);    
            return true;
        } catch (Exception err) {
            err.printStackTrace();
            return false;
        }
                
    }


    @Override
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException {
         return usuariosRepository.findByUsername(username);
        //return usuariosRepository.findByEmail(username);
    }
    
}
