package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Usuario;
import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity listar() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            usuarios = usuarioService.listar();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        
        return ResponseEntity.ok().body(usuarios);
    }

    @PostMapping()
    public ResponseEntity inserir(@RequestBody Usuario usuario) {
        if (
            usuario.getUsername() == null || 
            usuario.getPassword() == null)
            return ResponseEntity.status(400).body("Preencha username e password.");
        
        usuario = usuarioService.salvar(usuario);
        if (usuario.getId() != 0)
            return ResponseEntity.ok().body(usuario);

        return ResponseEntity.status(500)
                .body("Erro interno.");
    }

    @GetMapping("/{id}")
    public ResponseEntity exibir(@PathVariable("id") long id) {
        var usuario = usuarioService.exibir(id);
        return ResponseEntity.ok().body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") int id,
            @RequestBody Usuario usuario) {
        if (
            usuario.getUsername() == null || 
            usuario.getEmail() == null || 
            usuario.getPassword() == null)
            return ResponseEntity.status(400)
                    .body("Username, Email e Senha são obrigatórios.");
        
        usuario.setId(id);
        usuario = usuarioService.atualizar(usuario);
        return ResponseEntity.ok().body(usuario);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable("id") long id) {
        boolean result = usuarioService.excluir(id);
        if(!result){
            return ResponseEntity.internalServerError().body("nao foi possivel excluir")
        }
        return ResponseEntity.ok().body("Usuário excluido.");
    }

    @PatchMapping("/{id}")
    public ResponseEntity alterar(@PathVariable("id") int id,
            @RequestBody Usuario usuario) {
        usuario.setId(id);
        usuario = usuarioService.atualizar(usuario);
        return ResponseEntity.ok().body(usuario);
    }

    
}
