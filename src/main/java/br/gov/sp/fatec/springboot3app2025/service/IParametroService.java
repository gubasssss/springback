package br.gov.sp.fatec.springboot3app2025.service;

import java.util.List;

import br.gov.sp.fatec.springboot3app2025.entity.Parametro;

public interface IParametroService {

    public Parametro novoParametro(Parametro parametro);

    public List<Parametro> buscarTodos();

    public List<Parametro> buscarPorPalavraENomeUsuario(String palavra, String nomeUsuario);
}