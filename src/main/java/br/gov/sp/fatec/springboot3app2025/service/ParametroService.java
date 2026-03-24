package br.gov.sp.fatec.springboot3app2025.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.fatec.springboot3app2025.entity.Parametro;
import br.gov.sp.fatec.springboot3app2025.entity.Usuario;
import br.gov.sp.fatec.springboot3app2025.repository.ParametroRepository;
import br.gov.sp.fatec.springboot3app2025.repository.UsuarioRepository;

@Service
public class ParametroService implements IParametroService {

    @Autowired
    private ParametroRepository parametroRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Override
    public Parametro novoParametro(Parametro parametro) {
        if (parametro == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parâmetro inválido!");
        }

        if (parametro.getCriador() == null || parametro.getCriador().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário criador inválido!");
        }

        Optional<Usuario> usuarioOp = usuarioRepo.findById(parametro.getCriador().getId());
        if (usuarioOp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário criador não encontrado!");
        }

        if (parametro.getNome() == null || parametro.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do parâmetro inválido!");
        }

        if (parametro.getNome().matches(".*\\s.*") || parametro.getNome().matches(".*\\d.*")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O nome do parâmetro não pode conter espaços ou números!");
        }

        if (parametro.getValidade() != null && parametro.getValidade() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A validade, se informada, deve ser maior que zero!");
        }

        if (parametro.getDataHora() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data e hora inválidas!");
        }

        parametro.setCriador(usuarioOp.get());
        return parametroRepo.save(parametro);
    }

    @Override
    public List<Parametro> buscarTodos() {
        return parametroRepo.findAll();
    }

    @Override
    public List<Parametro> buscarPorPalavraENomeUsuario(String palavra, String nomeUsuario) {
        if (palavra == null || palavra.isBlank() || nomeUsuario == null || nomeUsuario.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Os parâmetros palavra e nomeUsuario devem ser preenchidos!");
        }

        return parametroRepo.findByNomeContainsAndCriadorNome(palavra, nomeUsuario);
    }
}