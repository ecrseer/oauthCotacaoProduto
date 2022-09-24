package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    
    @GetMapping
    public ResponseEntity index(){
        return ResponseEntity.status(200).body("Api Online");
    }
}
