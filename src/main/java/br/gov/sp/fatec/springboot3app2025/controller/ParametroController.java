package br.gov.sp.fatec.springboot3app2025.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.springboot3app2025.entity.Parametro;
import br.gov.sp.fatec.springboot3app2025.service.IParametroService;

@RestController
@CrossOrigin
@RequestMapping(value = "/parametro")
public class ParametroController {

    @Autowired
    private IParametroService service;

    @PostMapping
    public Parametro novoParametro(@RequestBody Parametro parametro) {
        return service.novoParametro(parametro);
    }

    @GetMapping
    public List<Parametro> buscarTodos() {
        return service.buscarTodos();
    }

    @GetMapping(value = "/buscar")
    public List<Parametro> buscarPorPalavraENomeUsuario(
            @RequestParam("palavra") String palavra,
            @RequestParam("nomeUsuario") String nomeUsuario) {
        return service.buscarPorPalavraENomeUsuario(palavra, nomeUsuario);
    }
}