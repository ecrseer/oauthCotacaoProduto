package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.model.Cotacao;

@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {
}
