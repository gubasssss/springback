package br.gov.sp.fatec.springboot3app2025.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.springboot3app2025.entity.Parametro;

public interface ParametroRepository extends JpaRepository<Parametro, Long> {

    public List<Parametro> findByNomeContainsAndCriadorNome(String palavra, String nome);

}