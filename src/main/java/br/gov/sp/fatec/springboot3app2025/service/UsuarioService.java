package br.gov.sp.fatec.springboot3app2025.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.fatec.springboot3app2025.repository.UsuarioRepository;
import br.gov.sp.fatec.springboot3app2025.entity.Usuario;
import br.gov.sp.fatec.springboot3app2025.entity.Autorizacao;
import br.gov.sp.fatec.springboot3app2025.repository.AutorizacaoRepository;
import org.springframework.security.access.prepost.PreAuthorize;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private AutorizacaoRepository autRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Usuario buscarPorId(Long id) {
        Optional<Usuario> usuarioOp = usuarioRepo.findById(id);
        if (usuarioOp.isPresent()) {
            return usuarioOp.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inválido!");
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Usuario novoUsuario(Usuario usuario) {
        if (usuario == null ||
            usuario.getNome() == null ||
            usuario.getSenha() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome e senha inválidos!");
        }

        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return usuarioRepo.save(usuario);
    }
    
    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Usuario> buscarTodos() {
        return usuarioRepo.findAll();
    }

    

    @Override
    @Transactional
    public Usuario novoUsuarioAutorizacao(String nome, String senha, String nomeAutorizacao) {
        Usuario usuario = new Usuario(nome, encoder.encode(senha));

        Optional<Autorizacao> autOp = autRepo.findByNome(nomeAutorizacao);
        Autorizacao autorizacao;

        if (autOp.isEmpty()) {
            autorizacao = new Autorizacao();
            autorizacao.setNome(nomeAutorizacao);
            autorizacao = autRepo.save(autorizacao);
        } else {
            autorizacao = autOp.get();
        }

        usuario.setAutorizacoes(new ArrayList<Autorizacao>());
        usuario.getAutorizacoes().add(autorizacao);

        return usuarioRepo.save(usuario);
    }

    
}