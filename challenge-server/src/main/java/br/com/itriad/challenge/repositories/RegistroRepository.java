package br.com.itriad.challenge.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.itriad.challenge.model.Registro;

public interface RegistroRepository extends JpaRepository<Registro, Long>{

	Registro findByPlacaAndSaidaIsNull(String Placa);
	List<Registro> findBySaidaIsNull();
}
