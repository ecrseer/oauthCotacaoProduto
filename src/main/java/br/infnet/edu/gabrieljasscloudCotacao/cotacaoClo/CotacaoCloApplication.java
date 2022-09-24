package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.service.UsuarioService;

@SpringBootApplication
public class CotacaoCloApplication implements CommandLineRunner{

	@Autowired
	UsuarioService usuarioService;

	public static void main(String[] args) {
		SpringApplication.run(CotacaoCloApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		/* var user = usuarioService.loadUserByUsername("tresquatro");
		System.out.print("\nuserrrr "+user.getUsername());
		 */
	}

}
